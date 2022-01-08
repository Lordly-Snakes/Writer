package com.antonyproduction.writer;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

public class AddAndEditClass extends BaseActClass {
    EditText ed;
    Button b;
    String id;
    MainAppClassContext mainAppClassContext;
    EditText edTitle;
    TextView timeText;
    Spinner spinner;
    Time timerecord;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        ed = findViewById(R.id.edText);
        edTitle = findViewById(R.id.edTextTitle);
        b = findViewById(R.id.ButOk);
        spinner = findViewById(R.id.prior);
        timeText = findViewById(R.id.time_v);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.base_list_item);
        spinner.setAdapter(arrayAdapter);
        arrayAdapter.addAll(Priority.values());
        Button del = findViewById(R.id.ButDel);
        Bundle data = getIntent().getExtras();
        id="";
        timeText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Time timeTime = Time.valueOf(timeText.getText().toString());

                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        LayoutInflater inflater = getLayoutInflater();
                        View view = inflater.inflate(R.layout.base_time_lay,null);
                        TimePicker time = view.findViewById(R.id.tim);
                        time.setHour(timeTime.getHours());
                        time.setMinute(timeTime.getMinutes());
                        time.setIs24HourView(true);
                        builder.setTitle("Заполните поля");
                        builder.setView(view);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK, so save the mSelectedItems results somewhere
                                // or return them to the component that opened the dialog
                                String timestr = "";
                                if (time.getMinute() < 10) {
                                    timestr = String.format("%s:0%s:00", time.getHour(), time.getMinute());
                                }
                                timestr = String.format("%s:%s:00", time.getHour(), time.getMinute());
                                setTimeText(
                                        Time.valueOf(timestr)
                                );
                            }
                        });
                        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                        builder.create().show();
                    }
                }
        );
        if(data != null){
            NoteRecord record = (NoteRecord)data.getSerializable(getResources().getString(R.string.keyEdit));
            ed.setText(record.getText());
            edTitle.setText(record.getTitle());
            id = data.getString(getResources().getString(R.string.posEdit));
            spinner.setSelection(record.getPriority().ordinal());
            timeText.setText(record.getTime().toString());
        }else {
            //del.set
            timeText.setText(new Time(System.currentTimeMillis()).toString());
            del.setVisibility(View.GONE);
            b.setEnabled(false);
        }
        mainAppClassContext = (MainAppClassContext) getApplicationContext();
        ed.addTextChangedListener(textWatcher);
    }



    private void setTimeText(Time time) {
        timeText.setText(time.toString());
    }


    public void toOK(View view) {
        Intent intent = getIntent();
        NoteRecord record = new NoteRecord(edTitle.getText().toString(),ed.getText().toString(),Time.valueOf(timeText.getText().toString()),(Priority) spinner.getSelectedItem());
        intent.putExtra(getResources().getString(R.string.keyEdit),record);
        if(!id.equals("")){
            intent.putExtra(getResources().getString(R.string.posEdit),id);
        }

        setResult(RESULT_OK,intent);
        finish();
    }
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String text = String.valueOf(s).trim();
            b.setEnabled(text.length()>0);

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void toDEl(View view) {
        mainAppClassContext.delNoteInTable(id);
        finish();
    }
}
