package com.example.fitnesscenter.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class ScheduledClass {

    int id, capacity, enrolled, weekday;
    String classType, difficulty, instructor;
    Calendar startTime, endTime;

    public ScheduledClass(int id, String classType, String instructor, int capacity, int enrolled, long startTime, long endTime, int weekday, String difficulty){
        this.id = id;
        this.classType = classType;
        this.instructor = instructor;
        this.capacity = capacity;
        this.enrolled = enrolled;
        this.startTime = Calendar.getInstance();
        this.startTime.setTime(new Date(startTime));
        this.endTime = Calendar.getInstance();
        this.endTime.setTime(new Date(endTime));
        this.weekday = weekday;
        this.difficulty = difficulty;
    }

    public int getId(){
        return id;
    }

    public int getCapacity(){
        return capacity;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public String getInstructor(){
        return instructor;
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

    public int getWeekday(){
        return weekday;
    }

    public String getDifficulty() { return difficulty; }

    @Override
    public boolean equals(Object other){
        if ( other instanceof ScheduledClass ){
            return ((ScheduledClass) other).getId() == id;
        }
        return false;
    }

}
