package com.antonyproduction.writer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Adapter extends BaseAdapter {
    private Context context;
    private final Random random;
    private List<NoteRecord> notes;
    private List<String> IDS;

    public Adapter(Context context,List<NoteRecord> list){
        this.context = context;
        MainAppClassContext mainAppClassContext = (MainAppClassContext) context.getApplicationContext();
        notes = list;
        IDS = mainAppClassContext.getListIDS();
        random = new Random();
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public NoteRecord getItem(int position) {

        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public String getItemIdInTable(int position) {
        return IDS.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if (convertView == null){
            LayoutInflater lay = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = lay.inflate(R.layout.base_list_linear,parent,false);

        }
        LinearLayout linearLayout = (LinearLayout) convertView;
        TextView tV = linearLayout.findViewById(R.id.edit_text);
        TextView tVtime = linearLayout.findViewById(R.id.edit_time_text);
        TextView tVtitle = linearLayout.findViewById(R.id.edit_title_text);
        TextView tVprior = linearLayout.findViewById(R.id.edit_prior_text);
        ColorView colorV = linearLayout.findViewById(R.id.colorV);
        colorV.setColorView(getRandomColor());
        tV.setText(notes.get(position).getText());
        tVtime.setText(notes.get(position).getTime().toString());
        tVtitle.setText(notes.get(position).getTitle());
        tVprior.setText(notes.get(position).getPriority().toString());
        return convertView;
    }

    int getRandomColor(){
        return Color.rgb(random.nextInt(256),random.nextInt(256),random.nextInt(256));
    }
}
