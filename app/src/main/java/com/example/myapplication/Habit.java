package com.example.myapplication;

import androidx.annotation.Nullable;

import java.util.Date;

public class Habit {
    String title;
    String desc;
    Date reminder;

    public Habit (String title, String desc, Date reminder){
        this.title = title;
        this.desc = desc;
        this.reminder = reminder;
    }

    public Habit (String title, String desc){
        this.title = title;
        this.desc = desc;
        this.reminder = null;
    }

    public Habit (String title){
        this.title = title;
        this.desc = null;
        this.reminder = null;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public void setReminder(Date reminder){
        this.reminder = reminder;
    }

}
