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
import com.example.fitnesscenter.helper.ClassType;

import java.util.ArrayList;

/**
 * Class that draws a link between a database cursor and a listview item
 * This displays the class types in the database
 */
public class ClassTypesAdapter extends ArrayAdapter<ClassType> {

    public ClassTypesAdapter(Context context, ArrayList<ClassType> classTypes) {
        super(context, 0, classTypes);
    }

    /**
     * Fills one listview item with the information from one class type
     * @param position in the list
     * @param convertView layout of the list item
     * @param parent parent layout
     * @return the filled in view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ClassType thisClassType = getItem(position);
        if ( convertView == null ){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_class_type, parent, false);
        }
        // Set the class type into the first textview
        TextView classTypeDisplay = convertView.findViewById(R.id.class_type_list_item_name);
        String classType = thisClassType.getName();
        classTypeDisplay.setText(classType);

        // Set the class description into the second textview
        TextView classDescriptionDisplay = convertView.findViewById(R.id.textview_list_item_class_type_description);
        String classDescription = thisClassType.getDescription();
        classDescriptionDisplay.setText(classDescription);

        return convertView;
    }
}
