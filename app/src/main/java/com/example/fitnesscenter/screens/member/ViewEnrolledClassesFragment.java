package com.example.fitnesscenter.screens.member;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnesscenter.R;
import com.example.fitnesscenter.database.ClassesAdapter;
import com.example.fitnesscenter.database.DBHelper;
import com.example.fitnesscenter.database.SharedPreferencesManager;
import com.example.fitnesscenter.helper.ScheduledClass;

import java.util.ArrayList;

public class ViewEnrolledClassesFragment extends Fragment {

    DBHelper database;
    String username;
    ListView enrolledClassesList;
    ArrayList<ScheduledClass> listItems;
    ClassesAdapter listAdapter;

    public static ViewEnrolledClassesFragment newInstance() {
        return new ViewEnrolledClassesFragment();
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_view_enrolled_classes, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){

        // Link to storage classes
        database = new DBHelper(getContext());
        SharedPreferencesManager SP = new SharedPreferencesManager(getContext());
        username = SP.getUsername();

        // Find views
        enrolledClassesList = view.findViewById(R.id.listView_enrolledClasses);

        // Populate list
        populateList();

        // Set context menu for list
        registerForContextMenu(enrolledClassesList);
    }

    private void populateList(){
        listItems = database.getMyEnrolledClasses(username);
        listAdapter = new ClassesAdapter(getContext(), listItems);
        enrolledClassesList.setAdapter(listAdapter);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.enrolled_class_context_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        // Get the class that was pressed
        int index = info.position;
        ScheduledClass thisClass = listItems.get(index);
        switch (item.getItemId()){
            case R.id.class_option_unenroll:
                database.unEnrollFromClass(username, thisClass.getId());
                getActivity().recreate();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
