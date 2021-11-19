package com.example.fitnesscenter.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class ScheduledClass {

    int id, capacity;
    String classType;
    Calendar startTime, endTime;

    public ScheduledClass(int id, String classType, int capacity, long startTime, long endTime){
        this.id = id;
        this.classType = classType;
        this.capacity = capacity;
        this.startTime = Calendar.getInstance();
        this.startTime.setTime(new Date(startTime));
        this.endTime = Calendar.getInstance();
        this.endTime.setTime(new Date(endTime));
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

    public Calendar getEndTime(){
        return endTime;
    }

}
