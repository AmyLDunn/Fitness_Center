package com.example.fitnesscenter.fragments;

import android.database.Cursor;
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
import com.example.fitnesscenter.database.ClassesCursorAdapter;
import com.example.fitnesscenter.database.DBHelper;

public class ViewAllScheduledEventsFragment extends Fragment {

    private DBHelper database;
    private Cursor classesCursor;
    private ClassesCursorAdapter cursorAdapter;

    public static ViewAllScheduledEventsFragment newInstance() {
        return new ViewAllScheduledEventsFragment();
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_view_all_scheduled_events, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        // Initializes the database
        database = new DBHelper(getActivity());

        // Finds all relevant views
        ListView classesList = getActivity().findViewById(R.id.list_of_all_scheduled_classes);
        EditText searchBar = getActivity().findViewById(R.id.search_bar);

        // Initializing the listview to contain all of the scheduled classes
        classesCursor = database.getAllClasses(null); // The searchkey will be used if the user is trying to search for a class type or instructor
        cursorAdapter = new ClassesCursorAdapter(getActivity(), classesCursor);
        classesList.setAdapter(cursorAdapter);

        // Adds a listener to the searchbar
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Updates the listview based on the searchBar text
                if ( searchBar.getText().toString().equals("") ){
                    classesCursor = database.getAllClasses(null);
                } else {
                    classesCursor = database.getAllClasses(searchBar.getText().toString());
                }
                cursorAdapter.changeCursor(classesCursor);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

}
