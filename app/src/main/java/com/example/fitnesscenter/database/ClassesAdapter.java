package com.example.fitnesscenter.database;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.fitnesscenter.R;
import com.example.fitnesscenter.helper.ScheduledClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Class that draws a link between a database cursor and a listview item
 * This displays the scheduled classes in the database
 */
public class ClassesAdapter extends ArrayAdapter<ScheduledClass> {

    public ClassesAdapter(Context context, ArrayList<ScheduledClass> classes) {
        super(context, 0, classes);
    }

    /**
     * Fills one listview item with the information from one scheduled class
     * @param position in the list
     * @param convertView layout of the list item
     * @param parent parent layout
     * @return the filled in view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ScheduledClass thisClass = getItem(position);
        if ( convertView == null ){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_class, parent, false);
        }

        // This is setting the type of class into the first TextView
        TextView nameDisplay = convertView.findViewById(R.id.class_list_item_name);
        String type = thisClass.getType();
        nameDisplay.setText(type);

        // This is setting the difficulty of the class into the second textview
        TextView difficultyDisplay = convertView.findViewById(R.id.class_list_item_difficulty);
        String difficulty = thisClass.getDifficulty();
        difficultyDisplay.setText("Difficulty: "+difficulty);

        // Setting the instructor name into the third TextView
        TextView instructorDisplay = convertView.findViewById(R.id.class_list_item_instructor);
        String instructor = thisClass.getInstructor();
        instructorDisplay.setText("Instructor: "+instructor);

        // Setting the capacity into the fourth TextView
        TextView capacityDisplay = convertView.findViewById(R.id.class_list_item_capacity);
        int enrolled = thisClass.getEnrolled();
        int capacity = thisClass.getCapacity();
        capacityDisplay.setText(formatClassCapacity(enrolled, capacity));

        // Collecting the date, start time, and end time and formatting them nicely before setting
        // them into the last TextView
        TextView timeDisplay = convertView.findViewById(R.id.class_list_item_time);
        timeDisplay.setText(thisClass.getClassScheduleString());

        return convertView;
    }

    /**
     * This formats the string to display the class capacity
     * @param enrolled is the number of people enrolled in the class
     * @param capacity is the total capacity of the class
     * @return is a String displaying the slots
     */
    public static String formatClassCapacity(int enrolled, int capacity){
        return "Capacity: "+enrolled+"/"+capacity+" - "+(capacity-enrolled)+" slots remaining";
    }
}
