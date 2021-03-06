package com.example.fitnesscenter.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fitnesscenter.R;
import com.example.fitnesscenter.database.DBHelper;
import com.example.fitnesscenter.database.SharedPreferencesManager;
import com.google.android.material.snackbar.Snackbar;

public class CreateNewAccountActivity extends AppCompatActivity {

    DBHelper database;
    int spinnerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        database = new DBHelper(this);
        Spinner accountTypeSpinner = (Spinner) findViewById(R.id.create_new_account_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.accountTypeChoices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeSpinner.setAdapter(adapter);
        spinnerPosition = 0;

        accountTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spinnerPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button createNewAccountButton = findViewById(R.id.create_new_account_button);
        createNewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewAccountDetails();
            }
        });
    }

    private void saveNewAccountDetails(){
        EditText usernameDisplay = findViewById(R.id.create_new_account_username);
        EditText passwordDisplay = findViewById(R.id.create_new_account_password);
        String username = usernameDisplay.getText().toString();
        String password = passwordDisplay.getText().toString();
        if ( database.accountExists(username) ) {
            Snackbar.make(findViewById(R.id.create_new_account_screen), "That username is already in use.", Snackbar.LENGTH_SHORT).show();
        } else if ( username.equals("") ) {
            Snackbar.make(findViewById(R.id.create_new_account_screen), "Please enter a username.", Snackbar.LENGTH_SHORT).show();
        } else if ( password.equals("") ) {
            Snackbar.make(findViewById(R.id.create_new_account_screen), "Please enter a password.", Snackbar.LENGTH_SHORT).show();
        } else {
            database.addAccount(username, password, spinnerPosition);
            SharedPreferencesManager SP = new SharedPreferencesManager(this);
            SP.setUsername(username);
            SP.setUserType(spinnerPosition);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}