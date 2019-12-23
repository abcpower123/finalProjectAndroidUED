package com.aszqsc.dontforgeteverything;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.aszqsc.dontforgeteverything.model.MyDatabaseHelper;
import com.aszqsc.dontforgeteverything.model.Note;

public class UIutil implements View.OnDragListener, View.OnLongClickListener {

    public static Drawable getRoundRect(int color) {
        RoundRectShape rectShape = new RoundRectShape(new float[]{
                10, 10, 10, 10,
                10, 10, 10, 10
        }, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(rectShape);
        shapeDrawable.getPaint().setColor(color);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.getPaint().setAntiAlias(true);
        shapeDrawable.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);
        return shapeDrawable;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public  static Button createButtonNote(int color, String title, Context context){
        Button b=new Button(context);
        b.setText(title);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 20, 20, 20);
        b.setPadding(20,20,20,20);
        b.setLayoutParams(params);
        b.setTextColor(Color.WHITE);
        b.setBackground(UIutil.getRoundRect(context.getColor(color)));
        return  b;
    }

    @Override
    public boolean onLongClick(View view) {
        MainActivity.showDeleteZone();

        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag().toString());
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        view.startDrag(data//data to be dragged
                , shadowBuilder //drag shadow
                , view//local data about the drag and drop operation
                , 0//no needed flags
        );
        return true;
    }



    @Override
    public boolean onDrag(final View view, final DragEvent event) {
        // Defines a variable to store the action type for the incoming event
        int action = event.getAction();
        // Handles each of the expected events
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                // Determines if this View can accept the dragged data
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    return true;
                }
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
                // Applies a YELLOW or any color tint to the View, when the dragged view entered into drag acceptable view
                // Return true; the return value is ignored.

                view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_OVER);

                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                // Ignore the event
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                view.getBackground().clearColorFilter();
                view.invalidate();
                return true;
            case DragEvent.ACTION_DROP:
                // Gets the item containing the dragged data
                ClipData.Item item = event.getClipData().getItemAt(0);
                // Gets the text data from the item.
                final String dragData = item.getText().toString();
                final View v = (View) event.getLocalState();
                final ViewGroup owner = (ViewGroup) v.getParent();
                //show delete dialog
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("Warning");
                final MyDatabaseHelper db=new MyDatabaseHelper(view.getContext());
                final Note n= db.getNote(Integer.parseInt(dragData));
                alert.setMessage("Are you sure to delete "+n.getTitle());
                alert.setCancelable(false);
                alert.setNegativeButton("No", null);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      db.deleteNote(n);
                        owner.removeView(v);//remove the dragged view
                    }
                });

                AlertDialog dialog = alert.create();
                dialog.show();

                // Returns true. DragEvent.getResult() will return true.
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                MainActivity.hideDeleteZone();
                view.getBackground().clearColorFilter();
                view.invalidate();
                return true;
            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }

}
