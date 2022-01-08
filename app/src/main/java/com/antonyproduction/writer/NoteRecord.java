package com.antonyproduction.writer;

import java.io.Serializable;
import java.sql.Time;


public class NoteRecord implements Serializable {
    private String title;
    private  String text;
    private Time time;
    private Priority priority;

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public NoteRecord(String title, String text, Time time, Priority priority) {
        this.title = title;
        this.text = text;
        this.time = time;
        this.priority = priority;
    }
//    public NoteRecord(String title, String text, Time time) {
//        this.title = title;
//        this.text = text;
//        this.time = time;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
