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

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_class, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameDisplay = view.findViewById(R.id.class_list_item_name);
        String username = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.CLASSES_COLUMN_TYPE));
        nameDisplay.setText(username);

        TextView instructorDisplay = view.findViewById(R.id.class_list_item_instructor);
        String instructor = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.CLASSES_COLUMN_INSTRUCTOR));
        instructorDisplay.setText("Instructor: "+instructor);

        TextView capacityDisplay = view.findViewById(R.id.class_list_item_capacity);
        int capacity = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.CLASSES_COLUMN_CAPACITY));
        capacityDisplay.setText("Capacity: "+capacity);

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
