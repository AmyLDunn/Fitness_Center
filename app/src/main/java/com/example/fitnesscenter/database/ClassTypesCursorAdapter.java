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

    /**
     * This method simply inflates (expands and adds) the list_item_class_type.xml format
     * and adds a single item to a ListView
     * @param context is the link to the complete application
     * @param cursor is the link to the database
     * @param parent is the viewgroup that the new layout could be part of
     * @return a new view that contains the data from one row of the cursor
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_class_type, parent, false);
    }

    /**
     * This takes the inflated view from newView above and fills in any data of the list item
     * according the to information in the cursor row
     * @param view This is the list_item_class_type.xml that was added
     * @param context is the link to the complete application
     * @param cursor This is the cursor link to the database
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Set the class type into the first textview
        TextView classTypeDisplay = view.findViewById(R.id.class_type_list_item_name);
        String classType = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.CLASS_TYPES_COLUMN_NAME));
        classTypeDisplay.setText(classType);

        // Set the class description into the second textview
        TextView classDescriptionDisplay = view.findViewById(R.id.textview_list_item_class_type_description);
        String classDescription = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.CLASS_TYPES_COLUMN_DESCRIPTION));
        classDescriptionDisplay.setText(classDescription);
    }
}
