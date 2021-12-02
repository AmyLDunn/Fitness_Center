package com.example.fitnesscenter.screens.instructor;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnesscenter.R;
import com.example.fitnesscenter.database.DBHelper;

import java.util.ArrayList;

public class ViewEnrolledMembersActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_enrolled_members);

        DBHelper database = new DBHelper(this);

        Bundle bundle = getIntent().getExtras();
        int classId = bundle.getInt("CLASS_ID");

        ListView membersListView = findViewById(R.id.listview_enrolledMembers);

        ArrayList<String> membersList = database.getEnrolledMembersOfClass(classId);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, membersList);
        membersListView.setAdapter(adapter);

    }

}
