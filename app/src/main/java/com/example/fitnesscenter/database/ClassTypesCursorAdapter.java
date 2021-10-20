package com.example.fitnesscenter.database;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.fitnesscenter.R;

public class ClassTypesCursorAdapter extends CursorAdapter {

    public ClassTypesCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.class_type_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView usernameDisplay = view.findViewById(R.id.class_type_list_item_name);
        String username = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.CLASS_TYPES_COLUMN_NAME));
        usernameDisplay.setText(username);
    }
}
