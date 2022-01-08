package com.antonyproduction.writer;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ToastHelper {
    Context context;
    public ToastHelper(Context context){
        this.context = context;
    }

    public void show(String text){
        Toast toast = new Toast(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout mainLay = (LinearLayout) inflater.inflate(R.layout.toast_lay,null);
        TextView textV = mainLay.findViewById(R.id.text);
        textV.setText(text);
        toast.setView(mainLay);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.show();
    }
}
