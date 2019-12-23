package com.aszqsc.dontforgeteverything;

import android.animation.Animator;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.aszqsc.dontforgeteverything.Notify.AlarmReceiver_SendOn;
import com.aszqsc.dontforgeteverything.customdialog.DatePickerFragment;
import com.aszqsc.dontforgeteverything.customdialog.TimePickerDialogFragment;
import com.aszqsc.dontforgeteverything.model.MyDatabaseHelper;
import com.aszqsc.dontforgeteverything.model.Note;

public class NoteInfo extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    MyDatabaseHelper db;
    Note note;


    // create an action bar button
    EditText txtTitle;
    EditText txtContent;
    TextView lbDate;
    TextView lbTime;
    CheckBox cNoti;
    Button btnSelectDate;
    Button btnSelectTime;
    boolean show = true;
    Spinner spinner;
    View notidatetimelayout;
    View containerLayout;

    int flagDatePicker = -1; //0: insert date; 1 : set notifyDate

    //appbar menu option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.savemenu) {
//            Log.e("button: ","clicled"+);
//        }
        switch (id) {
            case R.id.savemenu:
                note.setTitle(txtTitle.getText().toString());
                note.setContent(txtContent.getText().toString());
                note.setNoti(cNoti.isChecked());

                int day = 0, month = 0, year = 0, hour = 0, minute = 0;
                String[] date = btnSelectDate.getText().toString().split("/");
                String[] time = btnSelectTime.getText().toString().split(":");

                day = Integer.parseInt(date[0].trim());
                month = Integer.parseInt(date[1].trim());
                year = Integer.parseInt(date[2].trim());

                hour = Integer.parseInt(time[0].trim());
                minute = Integer.parseInt(time[1].trim());

                note.setDay(day);
                note.setMonth(month);
                note.setYear(year);
                note.setHour(hour);
                note.setMinute(minute);

                if (note.isNoti()) {
                    AlarmReceiver_SendOn.sendMess(day, month, year, hour, minute, NoteInfo.this, note.getTitle());
                }
                Log.e("Datetime", day + "/" + month + "/" + year + " " + hour + ":" + minute);
                Log.e("Save", note.toString());
                if (note.getId() == -1) {
                    db.addNote(note);

                } else {
                    db.updateNote(note);
                }
                onBackPressed();
                break;
            case R.id.deletemenu:
                db.deleteNote(note);
                onBackPressed();
                break;
            case R.id.insertdatemenu:
                DatePickerFragment dpf = new DatePickerFragment();
                flagDatePicker = 0;
                dpf.show(getSupportFragmentManager(), "Date picker quick insert");
                break;
            case R.id.pickcolormenu:
                showPickColorMenuDialog();
                break;
            case R.id.setpasswordmenu:
                showPasswordDialog();
                break;
        }
        Log.e("button: ", "clicled" + id);
        return super.onOptionsItemSelected(item);
    }

    // handle button activities
    private void showPickColorMenuDialog() {
        //layout
        LayoutInflater inflater = getLayoutInflater();
        View colorPickLayout = inflater.inflate(R.layout.colorpickerdialog, null);
        final Button c1 = colorPickLayout.findViewById(R.id.c1);
        final Button c2 = colorPickLayout.findViewById(R.id.c2);
        final Button c3 = colorPickLayout.findViewById(R.id.c3);
        final Button c4 = colorPickLayout.findViewById(R.id.c4);
        final Button c5 = colorPickLayout.findViewById(R.id.c5);
        final Button c6 = colorPickLayout.findViewById(R.id.c6);

        View.OnClickListener colorpickListener = new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Button b = (Button) view;

                Drawable background = b.getBackground();
                int color = ((ColorDrawable) background).getColor();

                if (color == getColor(R.color.note_c1))
                    changecolor(R.color.note_c1, R.color.note_c1_light, R.color.note_c1_lightest);
                if (color == getColor(R.color.note_c2))
                    changecolor(R.color.note_c2, R.color.note_c2_light, R.color.note_c2_lightest);
                if (color == getColor(R.color.note_c3))
                    changecolor(R.color.note_c3, R.color.note_c3_light, R.color.note_c3_lightest);
                if (color == getColor(R.color.note_c4))
                    changecolor(R.color.note_c4, R.color.note_c4_light, R.color.note_c4_lightest);
                if (color == getColor(R.color.note_c5))
                    changecolor(R.color.note_c5, R.color.note_c5_light, R.color.note_c5_lightest);
                if (color == getColor(R.color.note_c6))
                    changecolor(R.color.note_c6, R.color.note_c6_light, R.color.note_c6_lightest);

            }
        };

        c1.setOnClickListener(colorpickListener);
        c2.setOnClickListener(colorpickListener);
        c3.setOnClickListener(colorpickListener);
        c4.setOnClickListener(colorpickListener);
        c5.setOnClickListener(colorpickListener);
        c6.setOnClickListener(colorpickListener);
        //dialog
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Pick a note color");
        alert.setView(colorPickLayout);
        alert.setCancelable(true);
        alert.setNegativeButton("Cancel", null);


        AlertDialog dialog = alert.create();
        dialog.show();

    }

    private void showPasswordDialog() {
        //layout
        LayoutInflater inflater = getLayoutInflater();
        View passwordLayout = inflater.inflate(R.layout.setpassdialog, null);
        final EditText txtPass = passwordLayout.findViewById(R.id.txtPassword);

        //dialog
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Input password for note:");
        alert.setView(passwordLayout);
        alert.setCancelable(true);
        alert.setNegativeButton("Cancel", null);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                note.setPassword(txtPass.getText().toString());
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changecolor(int note_c1, int note_c1_light, int note_c1_lightest) {
        note.setColorTheme(note_c1);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(note_c1)));
        spinner.setBackground(new ColorDrawable(getColor(note_c1)));
        btnSelectTime.setBackground(new ColorDrawable(getColor(note_c1)));
        btnSelectDate.setBackground(new ColorDrawable(getColor(note_c1)));
        txtContent.setBackground(new ColorDrawable(getColor(note_c1_light)));
        containerLayout.setBackground(new ColorDrawable(getColor(note_c1_lightest)));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_info);
        //db
        db = new MyDatabaseHelper(this);
        //appbar
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.note_c6)));
        //get object
        note = (Note) getIntent().getSerializableExtra("note");
        //find views
        txtTitle = findViewById(R.id.txtTitle);
        txtContent = findViewById(R.id.txtContent);
        lbDate = findViewById(R.id.lbDate);
        lbTime = findViewById(R.id.lbTime);
        cNoti = findViewById(R.id.chNoti);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnSelectTime = findViewById(R.id.btnSelectTime);
        notidatetimelayout = findViewById(R.id.notidatetimelayout);
        spinner = findViewById(R.id.spinner);
        containerLayout = findViewById(R.id.parentNoteInfoLayout);
        cNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!cNoti.isChecked()) {
                    slideDown(notidatetimelayout);
                } else {
                    slideUp(notidatetimelayout);
                }
            }
        });

        setSeletedDateTimeNotify();
        initSpinner();
        //set data from the object got by intent
        txtTitle.setText(note.getTitle());
        txtContent.setText(note.getContent());
        cNoti.setChecked(note.isNoti());
        btnSelectDate.setText(note.getDay() + "/" + note.getMonth() + "/" + note.getYear());
        btnSelectTime.setText(note.getHour() + ":" + note.getMinute());
        spinner.setSelection(note.getCateID());
        int color = note.getColorTheme();
        if (color == R.color.note_c1)
            changecolor(R.color.note_c1, R.color.note_c1_light, R.color.note_c1_lightest);
        if (color == R.color.note_c2)
            changecolor(R.color.note_c2, R.color.note_c2_light, R.color.note_c2_lightest);
        if (color == R.color.note_c3)
            changecolor(R.color.note_c3, R.color.note_c3_light, R.color.note_c3_lightest);
        if (color == R.color.note_c4)
            changecolor(R.color.note_c4, R.color.note_c4_light, R.color.note_c4_lightest);
        if (color == R.color.note_c5)
            changecolor(R.color.note_c5, R.color.note_c5_light, R.color.note_c5_lightest);
        if (color == R.color.note_c6)
            changecolor(R.color.note_c6, R.color.note_c6_light, R.color.note_c6_lightest);
    }

    private void setSeletedDateTimeNotify() {
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Choose ", "date");
                DatePickerFragment dpf = new DatePickerFragment();
                flagDatePicker = 1;
                dpf.show(getSupportFragmentManager(), "Date picker set notify");
            }
        });
        btnSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Choose ", "time");
                TimePickerDialogFragment tpd = new TimePickerDialogFragment();
                tpd.show(getSupportFragmentManager(), "Set time notify");
            }
        });
    }


    // slide the view from below itself to the current position
    public void slideUp(final View view) {
//
//        TranslateAnimation animate = new TranslateAnimation(
//                0,                 // fromXDelta
//                0,                 // toXDelta
//                view.getHeight(),  // fromYDelta
//                0);                // toYDelta
//        animate.setDuration(500);
//        animate.setFillAfter(true);
//        animate.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//                view.setVisibility(View.VISIBLE);
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        view.startAnimation(animate);

        view.animate().translationYBy(-view.getHeight()).setDuration(500).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start();

    }

    // slide the view from its current position to below itself
    public void slideDown(final View view) {
//        TranslateAnimation animate = new TranslateAnimation(
//                0,                 // fromXDelta
//                0,                 // toXDelta
//                0,                 // fromYDelta
//                view.getHeight()); // toYDelta
//        animate.setDuration(500);
//        animate.setFillAfter(true);
//
//        animate.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                //view2.animate().scaleYBy(view.getHeight()).setDuration(500);
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                view.setVisibility(View.GONE);
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        view.startAnimation(animate);
        view.animate().translationYBy(view.getHeight()).setDuration(500).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start();
    }

    void initSpinner() {

        Spinner dropdown = findViewById(R.id.spinner);
        String[] items = new String[]{"Works", "Family", "Friend"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinneritemcustom, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                note.setCateID(i);
                Log.e("Seleted: ", i + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //dropdown.setOnItemSelectedListener(this);

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        i1++;
        if (flagDatePicker == 0) {
            if (txtTitle.hasFocus()) {
                txtTitle.setText(txtTitle.getText().toString() + " " + i2 + "/" + i1 + "/" + i + " ");
                // txtTitle.requestFocus();
                txtTitle.setSelection(txtTitle.getText().length());
            }
            if (txtContent.hasFocus()) {
                txtContent.setText(txtContent.getText().toString() + " " + i2 + "/" + i1 + "/" + i + " ");
                // txtContent.requestFocus();
                txtContent.setSelection(txtContent.getText().length());
            }
            Log.e("picked date: ", " " + i2 + "/" + i1 + "/" + i + " ");
        } else {
            btnSelectDate.setText(i2 + "/" + i1 + "/" + i + " ");
        }
        flagDatePicker = -1;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        btnSelectTime.setText(i + ":" + i1);
    }
}
