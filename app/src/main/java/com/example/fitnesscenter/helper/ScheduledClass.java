package com.example.fitnesscenter.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class ScheduledClass {

    int id, capacity;
    String classType, difficulty;
    Calendar startTime, endTime;

    public ScheduledClass(int id, String classType, int capacity, long startTime, long endTime, String difficulty){
        this.id = id;
        this.classType = classType;
        this.capacity = capacity;
        this.startTime = Calendar.getInstance();
        this.startTime.setTime(new Date(startTime));
        this.endTime = Calendar.getInstance();
        this.endTime.setTime(new Date(endTime));
        this.difficulty = difficulty;
    }

    public int getCapacity(){
        return capacity;
    }

    public String getType(){
        return classType;
    }

    public Calendar getStartTime(){
        return startTime;
    }

    public String getDateString(){
        return new SimpleDateFormat("MMMM d, yyyy").format(startTime.getTime());
    }

    public String getStartTimeString(){
        return new SimpleDateFormat("h:mm a").format(startTime.getTime());
    }

    public String getEndTimeString(){
        return new SimpleDateFormat("h:mm a").format(endTime.getTime());
    }

    public String getClassScheduleString(){
        return getDateString()+", "+getStartTimeString()+" to "+getEndTimeString();
    }

    public Calendar getEndTime(){
        return endTime;
    }

    public String getDifficulty() { return difficulty; }

}
