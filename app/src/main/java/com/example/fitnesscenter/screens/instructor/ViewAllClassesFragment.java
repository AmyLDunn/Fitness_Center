package com.example.fitnesscenter.screens.instructor;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnesscenter.R;
import com.example.fitnesscenter.database.ClassesAdapter;
import com.example.fitnesscenter.database.DBHelper;
import com.example.fitnesscenter.helper.ScheduledClass;

import java.util.ArrayList;

public class ViewAllClassesFragment extends Fragment {

    private DBHelper database;
    private ArrayList<ScheduledClass> classes;
    private ClassesAdapter classesAdapter;
    ListView classesList;

    public static ViewAllClassesFragment newInstance() {
        return new ViewAllClassesFragment();
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_view_all_classes, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        // Initializes the database
        database = new DBHelper(getActivity());

        // Finds all relevant views
        classesList = getActivity().findViewById(R.id.list_of_all_scheduled_classes);
        EditText searchBar = getActivity().findViewById(R.id.search_bar);

        // Initializing the listview to contain all of the scheduled classes
        classes = database.getAllClasses(""); // The searchkey will be used if the user is trying to search for a class type or instructor
        classesAdapter = new ClassesAdapter(getActivity(), classes);
        classesList.setAdapter(classesAdapter);

        // Adds a listener to the searchbar
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Updates the listview based on the searchBar text
                classes = database.getAllClasses(searchBar.getText().toString());
                classesAdapter = new ClassesAdapter(getContext(), classes);
                classesList.setAdapter(classesAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }



}
