package com.aszqsc.dontforgeteverything.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.aszqsc.dontforgeteverything.NoteInfo;
import com.aszqsc.dontforgeteverything.R;
import com.aszqsc.dontforgeteverything.UIutil;
import com.aszqsc.dontforgeteverything.model.MyDatabaseHelper;
import com.aszqsc.dontforgeteverything.model.Note;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FavFragment extends Fragment {
    MyDatabaseHelper db;
    FlexboxLayout container;
    ConstraintLayout bg;
    TextView txtTitle;

    int type; //0: works,1: family,2: friends.

    private FloatingActionButton btnadd;
    public FavFragment(MyDatabaseHelper db,int type){
        super();
        this.db=db;
        this.type=type;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_fav, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bg=view.findViewById(R.id.layoutbackground);
        txtTitle=view.findViewById(R.id.txtFragmentTitle);

        container =view.findViewById(R.id.uncategorycontainer);
        btnadd=view.findViewById(R.id.floatingActionButton2);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note a=new Note();
                a.setId(-1);
                a.setCateID(type);
                Intent t = new Intent(getContext(), NoteInfo.class);
                t.putExtra("note",a);
                startActivity(t);
            }
        });

        loadview();
        loadList();
//        containerr.addView(UIutil.createButtonNote(R.color.note_c3,"Somebutton",getContext()));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void loadview() {
        switch (type){
            case 0:
                txtTitle.setText("Work Note");
                changecolor(R.color.note_c4,R.color.note_c4_light,R.color.note_c4_lightest);
                break;
            case 1:
                txtTitle.setText("Family Notes");
                changecolor(R.color.note_c2,R.color.note_c2_light,R.color.note_c2_lightest);
                break;
            case 2:txtTitle.setText("Friend Notes");
                changecolor(R.color.note_c5,R.color.note_c5_light,R.color.note_c5_lightest);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changecolor(int note_c1, int note_c1_light, int note_c1_lightest) {
        bg.setBackground(new ColorDrawable(getContext().getColor(note_c1_lightest)));
        txtTitle.setBackground(new ColorDrawable(getContext().getColor(note_c1)));
        btnadd.setBackgroundTintList(ColorStateList.valueOf(getContext().getColor(note_c1)));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void loadList() {
        container.removeAllViews();
        List<Note> listNote=db.getAllNotesByCate(type);
        for (final Note a:listNote) {
            final Button b= UIutil.createButtonNote(a.getColorTheme(),a.getTitle(),getContext());
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(a.getPassword()!=null&&!a.getPassword().equals(""))
                    showPasswordDialog(a);
                    else{

                    Intent t = new Intent(getContext(), NoteInfo.class);
                    t.putExtra("note",a);
                    startActivity(t);}
                }
            });
            b.setTag(a.getId()+"");
            b.setOnLongClickListener(new UIutil());
            container.addView(b);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
        loadList();
    }
    private void showPasswordDialog(final Note note) {
        //layout
        LayoutInflater inflater = getLayoutInflater();
        View passwordLayout = inflater.inflate(R.layout.setpassdialog, null);
        final EditText txtPass =  passwordLayout.findViewById(R.id.txtPassword);

        //dialog
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Input password for note:");
        alert.setView(passwordLayout);
        alert.setCancelable(true);
        alert.setNegativeButton("Cancel", null);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!note.getPassword().equals(txtPass.getText().toString())){
                    Toast.makeText(getContext(),"Wrong password!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent t = new Intent(getContext(), NoteInfo.class);
                    t.putExtra("note",note);
                    startActivity(t);
                }


            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();

    }
}
