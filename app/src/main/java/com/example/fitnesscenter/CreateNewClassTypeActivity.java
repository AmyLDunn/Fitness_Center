package com.example.fitnesscenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fitnesscenter.database.DBHelper;
import com.google.android.material.snackbar.Snackbar;

public class CreateNewClassTypeActivity extends AppCompatActivity {

    DBHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_class_type);

        database = new DBHelper(this);

        Bundle bundle = getIntent().getExtras();
        int class_type_id = bundle.getInt("CLASS_TYPE_ID");
        String class_type_name = bundle.getString("CLASS_TYPE_NAME");
        String class_type_description = bundle.getString("CLASS_TYPE_DESCRIPTION");
        EditText classTypeNameDisplay = (EditText) findViewById(R.id.create_new_class_type_name);
        EditText classTypeDescDisplay = (EditText) findViewById(R.id.create_new_class_type_description);
        classTypeNameDisplay.setText(class_type_name);
        classTypeDescDisplay.setText(class_type_description);

        Button saveButton = findViewById(R.id.create_new_class_type_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String className = classTypeNameDisplay.getText().toString();
                String classDesc = classTypeDescDisplay.getText().toString();
                if (!database.classTypeExists(className)) {
                    if ( class_type_id == -1 ) {
                        database.addClassType(className, classDesc);
                    } else {
                        database.updateClassType(class_type_id, className, classDesc);
                    }
                    Intent returnIntent = new Intent();
                    setResult(RESULT_OK, returnIntent);
                    finish();
                } else {
                    Snackbar.make(findViewById(R.id.create_new_class_type_screen), "This class already exists!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

}