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

    /**
     * This is called upon pressing the login button
     * @param username the username that the user put in
     * @param password the password that the user put in
     */
    private void login(String username, String password) {

        // Verifying that account exists
        Account myAccount = database.getAccount(username, password);
        if (myAccount != null) {
            // Opening WelcomeActivity
            Intent intent = new Intent(this, WelcomeActivity.class);
            // Passing the Account object containing the user's account to the welcome screen
            intent.putExtra("USER_ACCOUNT", myAccount);
            startActivity(intent);
        }

    }

    /*

    Need to add a createAccount button as well!

     */

}