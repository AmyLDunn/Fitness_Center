package com.example.fitnesscenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcelable;

public class WelcomeActivity extends AppCompatActivity {

    Account userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // Gets the Account object that contains the user's information from the login screen.
        userAccount = getIntent().getParcelableExtra("USER_ACCOUNT");
    }
}