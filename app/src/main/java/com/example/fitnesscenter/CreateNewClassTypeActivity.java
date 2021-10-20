package com.example.fitnesscenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateNewClassTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_class_type);

        Bundle bundle = getIntent().getExtras();
        int class_type_id = bundle.getInt("CLASS_TYPE_ID");
        String class_type_name = bundle.getString("CLASS_TYPE_NAME");
        String class_type_description = bundle.getString("CLASS_TYPE_DESCRIPTION");

        // Todo: Add the appropriate entries for the class type name and description to activity_create_new_class_type.xml
        // Todo: Fill the entries of the xml using the two strings above (class_type_name and class_type_description)
        // Todo: Complete the saveButton listener by:
        //       - adding a new class type to the database (if id == -1, which can't exist)
        //         Remember to ask the database if the class type already exists and, if it does,
        //         print a message saying so (take a look at the Snackbars in LoginActivity.java)
        //       - updating the class type with the given id (if id >= 0)
        //       NOTE: Both of those options should go before making the intent that's already there.
        //             That intent is there to tell the class types list to update itself when we've added/changed an item

        Button saveButton = findViewById(R.id.create_new_class_type_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}