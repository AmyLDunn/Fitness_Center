package com.example.fitnesscenter.fragments;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnesscenter.R;

public class ViewAllScheduledEvents extends Fragment {

    public static ViewAllScheduledEvents newInstance() {
        return new ViewAllScheduledEvents();
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
        // TODO: Figure out the format of this tab in fragment_view_all_scheduled_events.xml.
        //       This fragment should have some way to search (probably a search bar at the top
        //       with a button to start the search) and a listview taking up the rest of the screen
        //       to show all of the classes.

        // TODO: After completing the xml file, use getActivity().findViewById(R.id._________) to
        //       get variables for the search bar, search button, and listview here.

    }

}
