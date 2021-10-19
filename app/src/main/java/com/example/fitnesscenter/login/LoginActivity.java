package com.example.fitnesscenter.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;

import com.example.fitnesscenter.mainapp.AdminActivity;
import com.example.fitnesscenter.newaccount.NewAccountActivity;
import com.example.fitnesscenter.R;
import com.example.fitnesscenter.helper.Account;
import com.example.fitnesscenter.helper.DBHelper;

public class LoginActivity extends AppCompatActivity {

    DBHelper database;

    Button login, createAccount;

    TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //DBHelper class element to be used when logging in
        database = new DBHelper(this);

        login = findViewById(R.id.login_button);
        createAccount = findViewById(R.id.createNewAccountButton);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                errorMessage = (TextView) findViewById((R.id.accountErrorMessage));
                EditText usernameEntry = (EditText) findViewById(R.id.usernameTextView);
                EditText passwordEntry = (EditText) findViewById(R.id.passwordTextView);
                String username = usernameEntry.getText().toString();
                String password = passwordEntry.getText().toString();

                login(username,password);

            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCreateAccount();
            }
        });

    }

    /**
     * This brings the user to the newAccountActivity page
     */
    private void goToCreateAccount(){
        Intent intent = new Intent(this, NewAccountActivity.class);
        startActivity(intent);
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
            //Clearing errorMessage
            errorMessage.setText("");
            // Opening WelcomeActivity
            Intent intent = new Intent(this, AdminActivity.class);
            // Passing the Account object containing the user's account to the welcome screen
            intent.putExtra("USER_ACCOUNT", myAccount);
            startActivity(intent);
        } else {
            errorMessage.setText("The username or password is incorrect!");
        }

    }

}