package com.example.fitnesscenter;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;

public class LoginActivity extends AppCompatActivity {

    DBHelper database;

    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //DBHelper class element to be used when logging in
        database = new DBHelper(this);

        login = findViewById(R.id.login_button);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                EditText usernameEntry = (EditText) findViewById(R.id.usernameTextView);
                EditText passwordEntry = (EditText) findViewById(R.id.passwordTextView);
                String username = usernameEntry.getText().toString();
                String password = passwordEntry.getText().toString();

                login(username,password);

            }
        });

    }

    private void login(String username, String password) {

        //Verifying that account exists
        if (database.accountExists(username)) {
            //Opening WelcomeActivity
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        }

    }

    /*

    Need to add a createAccount button as well!

     */

}