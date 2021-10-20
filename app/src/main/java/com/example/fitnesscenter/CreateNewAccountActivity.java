package com.example.fitnesscenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fitnesscenter.database.DBHelper;
import com.example.fitnesscenter.helper.Account;
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
        if ( database.accountExists(username) ){
            Snackbar.make(findViewById(R.id.create_new_account_screen), "That username is already in use.", Snackbar.LENGTH_SHORT).show();
        } else {
            database.addAccount(username, password, spinnerPosition);
            Account userAccount = new Account(username, spinnerPosition);
            Intent intent;
            if (userAccount.getType() == 0){
                intent = new Intent(this, MemberActivity.class);
            } else if (userAccount.getType() == 1){
                intent = new Intent(this, InstructorActivity.class);
            } else {
                intent = new Intent(this, AdminActivity.class);
            }
            intent.putExtra("USER_ACCOUNT", userAccount);
            startActivity(intent);
            finish();
        }
    }
}