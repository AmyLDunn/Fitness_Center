package com.example.fitnesscenter.screens.instructor;

import android.app.Activity;
import android.content.Intent;
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

import com.example.fitnesscenter.R;
import com.example.fitnesscenter.database.ClassesAdapter;
import com.example.fitnesscenter.database.DBHelper;
import com.example.fitnesscenter.database.SharedPreferencesManager;
import com.example.fitnesscenter.helper.ScheduledClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViewMyClassesFragment extends Fragment {

    DBHelper database;
    ArrayList<ScheduledClass> classes;
    ClassesAdapter classesAdapter;
    ListView classesList;

    String username;

    public static ViewMyClassesFragment newInstance() {
        ViewMyClassesFragment fragment = new ViewMyClassesFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_view_my_classes, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){

        // Initializing the listview to contain all of the scheduled classes
        database = new DBHelper(getActivity());
        SharedPreferencesManager SP = new SharedPreferencesManager(getContext());
        username = SP.getUsername();

        // Fills the listview
        classes = database.getMyClasses(username);
        classesList = getActivity().findViewById(R.id.list_of_my_scheduled_classes);
        classesAdapter = new ClassesAdapter(getActivity(), classes);
        classesList.setAdapter(classesAdapter);
        // This sets up the items in the classesList for long-presses
        registerForContextMenu(classesList);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), CreateNewClassActivity.class);
                intent.putExtra("CLASS_ID", -1);
                createNewClassLauncher.launch(intent);
            }
        });
    }

    /**
     * Creates a menu with the items in R.menu.class_context_menu when a list item is long-pressed
     * @param menu
     * @param v
     * @param menuInfo
     */
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.class_context_menu, menu);
    }

    /**
     * This is called when an option is chosen from the menu generated above
     * @param item
     * @return
     */
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        // Get the class that was pressed
        int index = info.position;
        ScheduledClass thisClass = classes.get(index);
        Intent intent;
        switch (item.getItemId()){
            case R.id.class_option_view_members:
                intent = new Intent(getActivity(), ViewEnrolledMembersActivity.class);
                intent.putExtra("CLASS_ID", thisClass.getId());
                startActivity(intent);
                return true;
            case R.id.class_option_edit:
                // Opens an intent
                intent = new Intent(getActivity(), CreateNewClassActivity.class);
                // Adds the class id to it
                intent.putExtra("CLASS_ID", thisClass.getId());
                // Launches the intent using a listener
                createNewClassLauncher.launch(intent);
                return true;
            case R.id.class_option_delete:
                // Deletes the class
                int id_to_delete = thisClass.getId();
                database.deleteClass(id_to_delete);
                // Refreshes the listview
                classes = database.getMyClasses(username);
                classesAdapter = new ClassesAdapter(getContext(), classes);
                classesList.setAdapter(classesAdapter);
                getActivity().recreate();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * Listens for when the CreateNewClassActivity is done and refreshes the page in case of changes
     */
    ActivityResultLauncher<Intent> createNewClassLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK ) {
                classes = database.getMyClasses(username);
                classesAdapter = new ClassesAdapter(getContext(), classes);
                classesList.setAdapter(classesAdapter);
                getActivity().recreate();
            }
        }
    });

}
