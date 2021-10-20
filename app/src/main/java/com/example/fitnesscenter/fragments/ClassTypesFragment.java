package com.example.fitnesscenter.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnesscenter.R;
import com.example.fitnesscenter.database.ClassTypesCursorAdapter;
import com.example.fitnesscenter.database.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class ClassTypesFragment extends Fragment {

    private DBHelper database;
    Cursor classTypeCursor;
    ClassTypesCursorAdapter cursorAdapter;

    public static ClassTypesFragment newInstance(){
        return new ClassTypesFragment();
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_class_types, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        database = new DBHelper(getActivity());
        classTypeCursor = database.getAllClassTypes();
        ListView classTypeList = (ListView) getActivity().findViewById(R.id.class_types_list);
        cursorAdapter = new ClassTypesCursorAdapter(getActivity(), classTypeCursor);
        classTypeList.setAdapter(cursorAdapter);
        registerForContextMenu(classTypeList);
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.class_types_context_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()){
            case R.id.class_type_option_edit:
                return true;
            case R.id.class_type_option_delete:
                classTypeCursor.moveToPosition(index);
                int id_to_delete = classTypeCursor.getInt(classTypeCursor.getColumnIndexOrThrow(DBHelper.CLASS_TYPES_COLUMN_ID));
                database.deleteClassType(id_to_delete);
                classTypeCursor = database.getAllClassTypes();
                cursorAdapter.changeCursor(classTypeCursor);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void onDestroyView(){
        super.onDestroyView();
    }
}
