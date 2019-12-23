package com.aszqsc.dontforgeteverything;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

public class UIutil {

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
}
