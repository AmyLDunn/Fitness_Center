package com.example.fitnesscenter.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class ScheduledClass {

    int id, classType, capacity;
    Date startTime, endTime;

    public ScheduledClass(int id, int classType, int capacity, long startTime, long endTime){
        this.id = id;
        this.classType = classType;
        this.capacity = capacity;
        this.startTime = new Date(startTime);
        this.endTime = new Date(endTime);
    }

    public int getCapacity(){
        return capacity;
    }

    public String getTime(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(new SimpleDateFormat("MMMM d, yyyy").format(startTime));
        buffer.append(", ");
        buffer.append(new SimpleDateFormat("h:mm a").format(startTime));
        buffer.append(" to ");
        buffer.append(new SimpleDateFormat("h:mm a").format(endTime));
        return buffer.toString();
    }

}
