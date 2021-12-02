package com.example.fitnesscenter.screens.member;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnesscenter.R;

public class ViewAvailableClassesFragment extends Fragment {

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
        // TODO: The layout file for this page (fragment_view_available_classes.xml) should have
        //       a searchbar (edittext) at the top to search by class type (jogging, swimming, etc).
        //       Underneath that search bar should be a spinner with all the days of the week and
        //       'All' at the top. This will be used to filter by the weekday.
        //       The rest of the screen is taken up by the listview.

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
