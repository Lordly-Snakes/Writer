package com.antonyproduction.writer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SQLHelper extends SQLiteOpenHelper {
    private final static String NAME_DATA="DBWriter6";
    private final static int VER_DATA=3;

    public SQLHelper(@Nullable Context context) {
        super(context, NAME_DATA,null, VER_DATA);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log.d("AAAAAAAAAAAAAAAAAAAAAAAAAAA","YES");
        db.execSQL(SQLTable.getStatement());
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
//        db.execSQL(SQLTable.getStatement());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public static class SQLTable{
        public static final String TABLE_NAME = "mainWriter";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_TEXT = "text";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_PRIORITY = "priority";
        public static String getStatement(){

            return String.format(
                    "CREATE TABLE %s(" +
                            "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "%s TEXT," +
                            "%s TEXT," +
                            "%s TEXT," +
                            "%s INT" +
                            ")",
                    TABLE_NAME,
                    BaseColumns._ID,
                    COLUMN_TITLE,
                    COLUMN_TEXT,
                    COLUMN_TIME,
                    COLUMN_PRIORITY
            );
        }
        public static long addNote(SQLiteDatabase db, NoteRecord note){
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE,note.getTitle());
            values.put(COLUMN_TEXT, note.getText());
            values.put(COLUMN_TIME, note.getTime().toString());
            values.put(COLUMN_PRIORITY, note.getPriority().ordinal());
            return db.insert(TABLE_NAME, null, values);
        }

        public static int updateNote(SQLiteDatabase db, NoteRecord note,String id){
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE,note.getTitle());
            values.put(COLUMN_TEXT, note.getText());
            values.put(COLUMN_TIME, note.getTime().toString());
            values.put(COLUMN_PRIORITY, note.getPriority().ordinal());
            return db.update(TABLE_NAME, values, BaseColumns._ID+"= ?",new String[]{id});
        }
        public static int deleteNote(SQLiteDatabase db,String id){
            return db.delete(TABLE_NAME, BaseColumns._ID+"= ?",new String[]{id});
        }
        public static Cursor getNote(SQLiteDatabase db, String id){
            return db.query(TABLE_NAME, null,BaseColumns._ID+"= ?",new String[]{id},null,null,COLUMN_PRIORITY);
        }
    }

}
