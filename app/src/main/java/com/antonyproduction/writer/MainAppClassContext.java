package com.antonyproduction.writer;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class MainAppClassContext extends Application {
   // private final List<NoteRecord> list = new ArrayList<NoteRecord>();
    private final List<String> IDS = new ArrayList<String>();
    ToastHelper toastHelper;
    SQLHelper helper;
    Cursor cursor;

    private  final  String CHANNEL_ID="sdafadf";
    private  final  int NOT_ADD_ID=0x001231;
    private  final  int NOT_EDIT_ID=0x001131;

    public MainAppClassContext(){
        super();
       helper = new SQLHelper(this);

        toastHelper = new ToastHelper(this);
    }

//    public List<NoteRecord> getList(){
//        return list;
//    }
    public List<String> getListIDS(){
        return IDS;
    }
    public SQLHelper getHelper(){
        return helper;
    }
    public void addNotes(NoteRecord record){

        addNoteInTable(record);
        //list.add(record);
        toastHelper.show("Запись добавлена");
        showNotification(NOT_ADD_ID,record.getText(),"Запись "+record.getTitle()+" добавлена",Main.class);
    }
    public  void setNotes(String id, NoteRecord record){
        //list.set()
        editNoteInTable(record,id);
        toastHelper.show("Запись изменена");
        showNotification(NOT_EDIT_ID,record.getText(),"Запись "+record.getTitle()+" изменена",Main.class);
    }

    public void showNotification(int id, String text,String title,Class<?> cls){
        Intent intent = new Intent(this, cls);  //класс активности, которая открывается при просмотре сообщения об уведомлении.  У Вас на этой активности может находиться, например, один TextView c текстом «Просмотр сообщения».
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

        RemoteViews view = new RemoteViews(getPackageName(), R.layout.not_lay);  //разметка для сообщения об уведомлении
        view.setImageViewResource(R.id.imagnot, R.mipmap.ic_launcher);
        view.setTextViewText(R.id.title_not_two, title);
        view.setTextViewText(R.id.text_not_two, text);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContent(view);
        Notification notification = builder.build();


        //notification.flags |= Notification.FLAG_NO_CLEAR;
        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(id, notification);//отправка уведомления
    }

    public void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        CharSequence name = "TEST_CHANNEL2";
        String description = "Тестовые сообщения2";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    public  void addNoteInTable(NoteRecord record){
        SQLHelper.SQLTable.addNote(helper.getWritableDatabase(),record);
    }

    public  void editNoteInTable(NoteRecord record,String id){
        SQLHelper.SQLTable.updateNote(helper.getWritableDatabase(),record,id);
    }

    public  void delNoteInTable(String id){
        SQLHelper.SQLTable.deleteNote(helper.getWritableDatabase(),id);
    }

    public NoteRecord getNoteInTable(String id){
        Cursor c = SQLHelper.SQLTable.getNote(helper.getReadableDatabase(),id);
        return new NoteRecord(
                c.getString(c.getColumnIndex(SQLHelper.SQLTable.COLUMN_TITLE)),
                c.getString(c.getColumnIndex(SQLHelper.SQLTable.COLUMN_TEXT)),
                Time.valueOf(c.getString(c.getColumnIndex(SQLHelper.SQLTable.COLUMN_TIME))),
                Priority.valueOf(c.getInt(c.getColumnIndex(SQLHelper.SQLTable.COLUMN_PRIORITY)))
        );
    }

    public void toDisplayList(List<NoteRecord> records) {
        records.clear();
        IDS.clear();
        loadNotes();
        if(cursor.moveToFirst()){
            do{
                records.add(
                        new NoteRecord(
                                cursor.getString(cursor.getColumnIndex(SQLHelper.SQLTable.COLUMN_TITLE)),
                                cursor.getString(cursor.getColumnIndex(SQLHelper.SQLTable.COLUMN_TEXT)),
                                Time.valueOf(cursor.getString(cursor.getColumnIndex(SQLHelper.SQLTable.COLUMN_TIME))),
                                Priority.valueOf(cursor.getInt(cursor.getColumnIndex(SQLHelper.SQLTable.COLUMN_PRIORITY)))
                        )
                );
                IDS.add(cursor.getString(cursor.getColumnIndex(BaseColumns._ID)));
            }while (cursor.moveToNext());
        }
    }
    public void loadNotes(){
        cursor = helper.getReadableDatabase().query(SQLHelper.SQLTable.TABLE_NAME,null,null,null,null,null, SQLHelper.SQLTable.COLUMN_PRIORITY);
    }


}
