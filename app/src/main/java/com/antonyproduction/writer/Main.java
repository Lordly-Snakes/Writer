package com.antonyproduction.writer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class Main extends BaseActClass {
    Adapter adapter;


    List<NoteRecord> list;
    MainAppClassContext mainAppClassContext;
    ListView listin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        list = new ArrayList<NoteRecord>();
        adapter = new Adapter(this,list);

        //adapter.addAll(getResources().getStringArray(R.array.arr));
        listin = findViewById(R.id.list_main);
        listin.setAdapter(adapter);
        listin.setOnItemClickListener(onItemClickListener);
        mainAppClassContext = (MainAppClassContext) getApplicationContext();
        mainAppClassContext.createNotificationChannel();

        mainAppClassContext.toDisplayList(list);
        listin.invalidateViews();
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent= new Intent(getApplicationContext(),AddAndEditClass.class);
            intent.putExtra(getResources().getString(R.string.keyEdit), adapter.getItem(position));
            intent.putExtra(getResources().getString(R.string.posEdit), adapter.getItemIdInTable(position));
            startActivityForResult(intent,EDIT_ACTION);

        }
    };


    public void toAdd(View v){
        Intent intent = new Intent(this,AddAndEditClass.class);
        startActivityForResult(intent,CREATE_ACTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) {
            Bundle bundle = data.getExtras();
            if(bundle!=null) {
                NoteRecord record;
                if (requestCode == EDIT_ACTION) {
                    String id = bundle.getString(getResources().getString(R.string.posEdit));
                    record = (NoteRecord)bundle.getSerializable(getResources().getString(R.string.keyEdit));

                    insertList(record,id);


                } else if (requestCode == CREATE_ACTION) {
//                    List<NoteRecord> list = mainAppClassContext.getList();
                    record = (NoteRecord)bundle.getSerializable(getResources().getString(R.string.keyEdit));
                    mainAppClassContext.addNotes(record);
                }

            }
        }
        mainAppClassContext.toDisplayList(list);
        listin.invalidateViews();
    }

    public void insertList(NoteRecord record,String id){
        mainAppClassContext.setNotes(id,record);
//                adapter.remove(adapter.get(id));
    }
}
