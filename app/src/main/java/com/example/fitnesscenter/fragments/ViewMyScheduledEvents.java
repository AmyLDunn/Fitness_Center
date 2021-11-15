package com.example.fitnesscenter.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnesscenter.R;

public class ViewMyScheduledEvents extends Fragment {

    public static ViewMyScheduledEvents newInstance() {
        return new ViewMyScheduledEvents();
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_view_my_scheduled_events, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        // TODO: Figure out the format of this tab in fragment_view_my_scheduled_events.xml.
        //       This fragment should have a listview that takes the entire screen to show all
        //       of the classes that the instructor is teaching. It should also have a floating
        //       action button for the instructor to schedule new classes.

        // TODO: After completing the xml file, use getActivity().findViewById(R.id._________) to
        //       get variables for the listview and floating action button here

        // TODO: The floating action button should have an onClickListener that will open
        //       CreateNewClassActivity
    }

}
