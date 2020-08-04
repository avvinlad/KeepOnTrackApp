package com.example.myapplication;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Habit {
    String title;
    String desc;
    String reminder;

    public Habit (String title, String desc, String reminder){
        this.title = title;
        this.desc = desc;
        this.reminder = reminder;
    }

    public Habit(Habit newHabit){
        this.title = newHabit.title;
        this.desc = newHabit.desc;
        this.reminder = newHabit.reminder;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public void setReminder(String reminder){
        this.reminder = reminder;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDesc(){
        return this.desc;
    }

    public String getReminder(){
        return this.reminder;
    }

}
