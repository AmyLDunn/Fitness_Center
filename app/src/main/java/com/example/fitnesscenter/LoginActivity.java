package com.example.fitnesscenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fitnesscenter.database.DBHelper;
import com.example.fitnesscenter.helper.Account;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    DBHelper database;
    Button loginButton, createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = new DBHelper(this);
        loginButton = findViewById(R.id.login_button);
        createAccountButton = findViewById(R.id.login_new_account);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView usernameInput = (TextView) findViewById(R.id.login_username);
                TextView passwordInput = (TextView) findViewById(R.id.login_password);
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                Account myAccount = database.getAccount(username, password);
                if ( myAccount == null ) {
                    if ( !database.accountExists(username) ) {
                        Snackbar.make(findViewById(R.id.login_screen), "That account does not exist.", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(findViewById(R.id.login_screen), "That is the wrong password.", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    //Resetting text inputs of username and password
                    usernameInput.setText("");
                    passwordInput.setText("");

                    //Logging in
                    login(myAccount);
                }
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewAccount();
            }
        });
    }

    private void login(Account myAccount){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("USER_ACCOUNT", myAccount);
        startActivity(intent);
    }

    private void createNewAccount(){
        Intent intent = new Intent(this, CreateNewAccountActivity.class);
        startActivity(intent);
    }
}