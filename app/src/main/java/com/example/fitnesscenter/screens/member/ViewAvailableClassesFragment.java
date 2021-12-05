package com.example.fitnesscenter.screens.member;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnesscenter.R;
import com.example.fitnesscenter.database.ClassesAdapter;
import com.example.fitnesscenter.database.DBHelper;
import com.example.fitnesscenter.database.SharedPreferencesManager;
import com.example.fitnesscenter.helper.ScheduledClass;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ViewAvailableClassesFragment extends Fragment {

    private DBHelper database;
    String username;
    private EditText classType;
    private Spinner dayOfWeek;
    ListView classesList;

    ArrayList<ScheduledClass> classes;
    ClassesAdapter classesAdapter;

    public static ViewAvailableClassesFragment newInstance() {
        return new ViewAvailableClassesFragment();
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_view_available_classes, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){

        // Link to the database
        database = new DBHelper(getContext());
        SharedPreferencesManager SP = new SharedPreferencesManager(getContext());
        username = SP.getUsername();

        // Finds the views
        classType = getActivity().findViewById(R.id.class_search);
        dayOfWeek = getActivity().findViewById(R.id.day_of_week);
        classesList = getActivity().findViewById(R.id.listView_availableClasses);

        // Fills the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.weekdays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayOfWeek.setAdapter(adapter);

        // Filling the listview
        classes = database.getAvailableClasses(username, "", -1); // The searchkey will be used if the user is trying to search for a class type or instructor
        classesAdapter = new ClassesAdapter(getContext(), classes);
        classesList.setAdapter(classesAdapter);
        // Register listview for context menu
        registerForContextMenu(classesList);

        // Sets listeners
        classType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                classes = database.getAvailableClasses(username, classType.getText().toString(), dayOfWeek.getSelectedItemPosition()); // The searchkey will be used if the user is trying to search for a class type or instructor
                classesAdapter = new ClassesAdapter(getContext(), classes);
                classesList.setAdapter(classesAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        dayOfWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                classes = database.getAvailableClasses(username, classType.getText().toString(), dayOfWeek.getSelectedItemPosition()); // The searchkey will be used if the user is trying to search for a class type or instructor
                classesAdapter = new ClassesAdapter(getContext(), classes);
                classesList.setAdapter(classesAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.available_class_context_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        // Get the class that was pressed
        int index = info.position;
        ScheduledClass thisClass;
        switch (item.getItemId()){
            case R.id.class_option_enroll:
                thisClass = classes.get(index);
                if ( database.courseConflict(username, thisClass.getId()) ) {
                    Snackbar.make(getView(), "There is a time conflict with that course. Check your enrolled courses.", Snackbar.LENGTH_SHORT).show();
                } else {
                    database.enrollInClass(username, thisClass.getId());
                    getActivity().recreate();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
