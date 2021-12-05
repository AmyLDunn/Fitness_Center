package com.example.fitnesscenter.screens.member;

import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.example.fitnesscenter.database.DBHelper;

public class ViewAvailableClassesFragment extends Fragment {

    private DBHelper database;
    private EditText classType;
    private Spinner dayOfWeek;
    ListView classesList;
    int spinnerPosition;

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

        //Finds the class type the user is searching for
        classType = getActivity().findViewById(R.id.list_of_all_scheduled_classes);

        //Finds the day of week the user is searching for
        /*
        * 52.) R.array.accountTypeChoices ~ what do I reference for the dates?
         */
        dayOfWeek = (Spinner) getActivity().findViewById(R.id.day_of_week);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.accountTypeChoices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayOfWeek.setAdapter(adapter);
        spinnerPosition = 0;

        dayOfWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spinnerPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // TODO: In this file, make variables and find all the relevant views (listview, searchbar,
        //       weekday dropdown).

        // TODO: Make a method to fill the listview using fragments/instructor/ViewAllClassesFragment.java
        //       lines 26-29, 47, 50, and 54-56 as an example. Use database.getAvailableClasses()
        //       to get the ArrayList<ScheduledClass>.
        //       Arguments for the database.getAvailableClasses() method:
        //            String username -> pass in the logged in user's name
        //                               Get this by making a new SharedPreferencesManager object and
        //                               calling getUsername() on it
        //            String searchType -> Get from the searchbar (edittext)
        //            int weekday -> -1 if the spinner is 'All' or the weekday number if it's not 'All'

        // TODO: Add listeners for the searching.
        //       Upon typing in the searchbar, it should refresh the page (see
        //       fragments/instructor/ViewAllClassesFragment.java from lines 59 to 79 for an example).
        //       Upon changing the spinner value (the weekdays), it should refresh the page as well.

        // TODO: Register the listview for a context menu so that a long-press brings up a popup menu
        //       with the option to register for the course. You can use database.courseConflict(username, courseId)
        //       to check if the user has any conflicting times. Up to you how you deal with conflicts.

    }

}
