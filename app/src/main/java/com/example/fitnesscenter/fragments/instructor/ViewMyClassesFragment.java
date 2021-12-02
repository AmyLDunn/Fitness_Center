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
import com.example.fitnesscenter.database.ClassesCursorAdapter;
import com.example.fitnesscenter.database.DBHelper;
import com.example.fitnesscenter.helper.Account;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewMyClassesFragment extends Fragment {

    DBHelper database;
    Cursor classesCursor;
    ClassesCursorAdapter cursorAdapter;

    Account myAccount;

    public static ViewMyClassesFragment newInstance(Account myAccount) {
        ViewMyClassesFragment fragment = new ViewMyClassesFragment();
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
        return inflater.inflate(R.layout.fragment_view_my_classes, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){

        // Initializing the listview to contain all of the scheduled classes
        database = new DBHelper(getActivity());

        classesCursor = database.getMyClasses(myAccount.getUsername());
        ListView classesList = getActivity().findViewById(R.id.list_of_my_scheduled_classes);
        cursorAdapter = new ClassesCursorAdapter(getActivity(), classesCursor);
        classesList.setAdapter(cursorAdapter);
        registerForContextMenu(classesList);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), CreateNewClassActivity.class);
                intent.putExtra("CLASS_ID", -1);
                intent.putExtra("USER_ACCOUNT", myAccount);
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
        classesCursor.moveToPosition(index);
        switch (item.getItemId()){
            case R.id.class_option_edit:
                Intent intent = new Intent(getActivity(), CreateNewClassActivity.class);
                intent.putExtra("CLASS_ID", classesCursor.getInt(classesCursor.getColumnIndexOrThrow(DBHelper.CLASSES_COLUMN_ID)));
                intent.putExtra("USER_ACCOUNT", myAccount);
                createNewClassLauncher.launch(intent);
                return true;
            case R.id.class_option_delete:
                int id_to_delete = classesCursor.getInt(classesCursor.getColumnIndexOrThrow(DBHelper.CLASSES_COLUMN_ID));
                database.deleteClass(id_to_delete);
                classesCursor = database.getMyClasses(myAccount.getUsername());
                cursorAdapter.changeCursor(classesCursor);
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
                classesCursor = database.getMyClasses(myAccount.getUsername());
                cursorAdapter.changeCursor(classesCursor);
                getActivity().recreate();
            }
        }
    });

}
