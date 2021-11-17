package com.example.fitnesscenter;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnesscenter.database.DBHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CreateNewClassActivity extends AppCompatActivity {

    DBHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_class);

        //Drop-down list displaying all the class types
        Spinner spin = (Spinner) findViewById(R.id.select_class_type);
        // Get the arrayList from the database
        ArrayList<String> classTypeNames = database.getAllClassTypeNames();
        // Pass the arrayList to the adapter
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classTypeNames);
        // Tell the adapter that it's meant to be a dropdown
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Assign the adapter to the spinner (use whatever Spinner variable you made)
        spin.setAdapter(spinnerArrayAdapter);


        EditText startDateDisplay = findViewById(R.id.edit_class_date);
        startDateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = new GregorianCalendar();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i = year;
                        i1 = month;
                        i2 = day;
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

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


        //FOr input android.focusable=false
        database = new DBHelper(this);

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("CLASS_ID");
        String class_type_name = bundle.getString("CLASS_TYPE_NAME");
        String class_type_description = bundle.getString("CLASS_TYPE_DESCRIPTION");
    }

}