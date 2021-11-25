package com.example.fitnesscenter.fragments;

import android.app.Activity;
import android.content.Intent;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnesscenter.CreateNewClassTypeActivity;
import com.example.fitnesscenter.R;
import com.example.fitnesscenter.database.ClassTypesCursorAdapter;
import com.example.fitnesscenter.database.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class ClassTypesFragment extends Fragment {

    private DBHelper database;
    private Cursor classTypeCursor;
    private ClassTypesCursorAdapter cursorAdapter;

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
        // Initializes the database
        database = new DBHelper(getActivity());

        // Finds all views
        ListView classTypeList = (ListView) getActivity().findViewById(R.id.class_types_list);
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);

        // Fills the listview and registers its context menu
        classTypeCursor = database.getAllClassTypes();
        cursorAdapter = new ClassTypesCursorAdapter(getActivity(), classTypeCursor);
        classTypeList.setAdapter(cursorAdapter);
        registerForContextMenu(classTypeList);

        // Sets the onClick for the floating action button
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), CreateNewClassTypeActivity.class);
                intent.putExtra("CLASS_TYPE_ID", -1);
                intent.putExtra("CLASS_TYPE_NAME", "");
                intent.putExtra("CLASS_TYPE_DESCRIPTION", "");
                createNewClassTypeLauncher.launch(intent);
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
        // Moves cursor to relevant item
        int index = info.position;
        classTypeCursor.moveToPosition(index);
        switch (item.getItemId()){
            case R.id.class_type_option_edit:
                // Builds the intent to open the edit page
                Intent intent = new Intent(getActivity(), CreateNewClassTypeActivity.class);
                // Adds the info about the class to the intent
                intent.putExtra("CLASS_TYPE_ID", classTypeCursor.getInt(classTypeCursor.getColumnIndexOrThrow(DBHelper.CLASS_TYPES_COLUMN_ID)));
                intent.putExtra("CLASS_TYPE_NAME", classTypeCursor.getString(classTypeCursor.getColumnIndexOrThrow(DBHelper.CLASS_TYPES_COLUMN_NAME)));
                intent.putExtra("CLASS_TYPE_DESCRIPTION", classTypeCursor.getString(classTypeCursor.getColumnIndexOrThrow(DBHelper.CLASS_TYPES_COLUMN_DESCRIPTION)));
                // Launches the intent with a result listener
                createNewClassTypeLauncher.launch(intent);
                return true;
            case R.id.class_type_option_delete:
                // deletes the class type from the database
                int id_to_delete = classTypeCursor.getInt(classTypeCursor.getColumnIndexOrThrow(DBHelper.CLASS_TYPES_COLUMN_ID));
                database.deleteClassType(id_to_delete);
                // Refreshes the listview
                classTypeCursor = database.getAllClassTypes();
                cursorAdapter.changeCursor(classTypeCursor);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    ActivityResultLauncher<Intent> createNewClassTypeLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK ) {
                // Refreshes the listview
                classTypeCursor = database.getAllClassTypes();
                cursorAdapter.changeCursor(classTypeCursor);
            }
        }
    });
}
