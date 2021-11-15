package com.example.fitnesscenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CreateNewClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_class);

        // TODO: Edit the activity_create_new_class.xml to include all of the needed input fields.
        //       The class type (as a Spinner) <- this is a form of a dropdown list
        //       The date (as an EditText) <- set android:focusable="false"
        //       The start time (as an EditText) <- set android:focusable="false"
        //       The end time (as an EditText) <- set android:focusable="false"
        //       The capacity (as a NumberPicker)

        // TODO: Connect all the required entry fields to this java file with findViewById() and
        //       add Calendar variables to hold the date, startTime, and endTime values

        // TODO: The EditText dateDisplay variable should have an onClickListener that opens a
        //       DatePickerDialog (already a thing in Android Studio). The default date should be
        //       today (aka the current date) and upon choosing a date, it prints the chosen date
        //       into the EditText dateDisplay.

        // TODO: The EditText startTimeDisplay and EditText endTimeDisplau should both have
        //       separate onClickListeners that open TimePickerDialogs (already a thing in Android
        //       Studio). The default time should be noon and upon choosing a time, it prints the
        //       chosen time to the appropriate EditText startTimeDisplay or endTimeDisplay.
    }
}