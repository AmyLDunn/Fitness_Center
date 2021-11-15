package com.example.fitnesscenter.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class ScheduledClass {

    int id, capacity;
    String classType;
    Date startTime, endTime;

    public ScheduledClass(int id, String classType, int capacity, long startTime, long endTime){
        this.id = id;
        this.classType = classType;
        this.capacity = capacity;
        this.startTime = new Date(startTime);
        this.endTime = new Date(endTime);
    }

    public int getCapacity(){
        return capacity;
    }

    public String getType(){
        return classType;
    }

    public Date getStartTime(){
        return startTime;
    }

    public Date getEndTime(){
        return endTime;
    }

}
