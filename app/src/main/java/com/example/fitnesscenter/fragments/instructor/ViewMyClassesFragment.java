package com.example.fitnesscenter.fragments.instructor;

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

import com.example.fitnesscenter.CreateNewClassActivity;
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

        classes = database.getMyClasses(username);
        classesList = getActivity().findViewById(R.id.list_of_my_scheduled_classes);
        classesAdapter = new ClassesAdapter(getActivity(), classes);
        classesList.setAdapter(classesAdapter);
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

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.class_context_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        ScheduledClass thisClass = classes.get(index);
        switch (item.getItemId()){
            case R.id.class_option_edit:
                Intent intent = new Intent(getActivity(), CreateNewClassActivity.class);
                intent.putExtra("CLASS_ID", thisClass.getId());
                createNewClassLauncher.launch(intent);
                return true;
            case R.id.class_option_delete:
                int id_to_delete = thisClass.getId();
                database.deleteClass(id_to_delete);
                classes = database.getMyClasses(username);
                classesAdapter = new ClassesAdapter(getContext(), classes);
                classesList.setAdapter(classesAdapter);
                getActivity().recreate();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

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
