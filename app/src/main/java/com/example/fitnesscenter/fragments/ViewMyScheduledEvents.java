package com.example.fitnesscenter.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnesscenter.R;
import com.example.fitnesscenter.database.ClassesCursorAdapter;
import com.example.fitnesscenter.database.DBHelper;
import com.example.fitnesscenter.helper.Account;

public class ViewMyScheduledEvents extends Fragment {

    DBHelper database;
    Cursor classesCursor;
    ClassesCursorAdapter cursorAdapter;

    Account myAccount;

    public static ViewMyScheduledEvents newInstance(Account myAccount) {
        ViewMyScheduledEvents fragment = new ViewMyScheduledEvents();
        Bundle bundle = new Bundle();
        bundle.putParcelable("USER_ACCOUNT", myAccount);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = getActivity().getIntent().getExtras();
        myAccount = bundle.getParcelable("USER_ACCOUNT");
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
        // Initializing the listview to contain all of the scheduled classes
        database = new DBHelper(getActivity());

        classesCursor = database.getMyClasses(myAccount.getUsername());
        ListView classesList = getActivity().findViewById(R.id.list_of_all_scheduled_classes);
        cursorAdapter = new ClassesCursorAdapter(getActivity(), classesCursor);
        classesList.setAdapter(cursorAdapter);

        // TODO: The floating action button should have an onClickListener that will open
        //       CreateNewClassActivity
    }

}
