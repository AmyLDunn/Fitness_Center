package com.example.fitnesscenter.database;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.fitnesscenter.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClassesCursorAdapter extends CursorAdapter {

    public ClassesCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    /**
     * This method simply inflates (expands and adds) the list_item_class.xml format
     * and adds a single item to a ListView
     * @param context
     * @param cursor
     * @param parent
     * @return
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_class, parent, false);
    }

    /**
     * This takes the inflated view from newView above and fills in any data of the list item
     * according the to information in the cursor row
     * @param view This is the list_item_class.xml that was added
     * @param context
     * @param cursor This is the cursor link to the database
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // This is setting the type of class into the first TextView
        TextView nameDisplay = view.findViewById(R.id.class_list_item_name);
        String username = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.CLASSES_COLUMN_TYPE));
        nameDisplay.setText(username);

        // Setting the instructor name into the second TextView
        TextView instructorDisplay = view.findViewById(R.id.class_list_item_instructor);
        String instructor = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.CLASSES_COLUMN_INSTRUCTOR));
        instructorDisplay.setText("Instructor: "+instructor);

        // Setting the capacity into the third TextView
        TextView capacityDisplay = view.findViewById(R.id.class_list_item_capacity);
        int capacity = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.CLASSES_COLUMN_CAPACITY));
        capacityDisplay.setText("Capacity: "+capacity);

        // Collecting the date, start time, and end time and formatting them nicely before setting
        // them into the last TextView
        TextView timeDisplay = view.findViewById(R.id.class_list_item_time);
        Date startTime = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.CLASSES_COLUMN_START)));
        Date endTime = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.CLASSES_COLUMN_END)));
        StringBuffer buffer = new StringBuffer();
        buffer.append(new SimpleDateFormat("MMMM d, yyyy").format(startTime));
        buffer.append(", ");
        buffer.append(new SimpleDateFormat("h:mm a").format(startTime));
        buffer.append(" to ");
        buffer.append(new SimpleDateFormat("h:mm a").format(endTime));
        timeDisplay.setText(buffer.toString());
    }
}
