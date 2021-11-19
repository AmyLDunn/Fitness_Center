package com.example.fitnesscenter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnesscenter.database.DBHelper;
import com.example.fitnesscenter.helper.Account;
import com.example.fitnesscenter.helper.ScheduledClass;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CreateNewClassActivity extends AppCompatActivity {

    DBHelper database;

    Account userAccount;

    String type;
    Calendar date;
    Calendar startTime;
    Calendar endTime;
    NumberPicker picker;
    int capacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_class);

        database = new DBHelper(this);

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("CLASS_ID");
        userAccount = bundle.getParcelable("USER_ACCOUNT");

        if ( id == -1 ){ // Making a new scheduled class
            date = new GregorianCalendar();
            startTime = new GregorianCalendar();
            endTime = new GregorianCalendar();
            startTime.set(Calendar.HOUR, 12);
            startTime.set(Calendar.MINUTE, 0);
            endTime.set(Calendar.HOUR, 12);
            endTime.set(Calendar.MINUTE, 0);
            capacity = 10;
        } else {
            ScheduledClass scheduledClass = database.getClass(id);
            type = scheduledClass.getType();
            capacity = scheduledClass.getCapacity();
            date = scheduledClass.getStartTime();
            startTime = scheduledClass.getStartTime();
            endTime = scheduledClass.getEndTime();
        }

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
        if ( id != -1 ) {
            spin.setSelection(spinnerArrayAdapter.getPosition(type));
        }


        EditText startDateDisplay = findViewById(R.id.edit_class_date);
        startDateDisplay.setText(new SimpleDateFormat("MMMM d, yyyy").format(date.getTime()));
        startDateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = date.get(Calendar.YEAR);
                int month = date.get(Calendar.MONTH);
                int day = date.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        date.set(Calendar.YEAR, year);
                        date.set(Calendar.MONTH, month);
                        date.set(Calendar.DAY_OF_MONTH, day);
                        Date thisDate = date.getTime();
                        startDateDisplay.setText(new SimpleDateFormat("MMMM d, yyyy").format(thisDate));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        EditText startTimeDisplay = findViewById(R.id.edit_class_start_time);
        startTimeDisplay.setText(new SimpleDateFormat("h:mm a").format(startTime.getTime()));
        startTimeDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startHour = startTime.get(Calendar.HOUR);
                int startMinute = startTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        startTime.set(Calendar.HOUR_OF_DAY, hour);
                        startTime.set(Calendar.MINUTE, minute);
                        Date thisDate = startTime.getTime();
                        startTimeDisplay.setText(new SimpleDateFormat("h:mm a").format(thisDate));
                    }
                }, startHour, startMinute, false);
                timePickerDialog.show();
            }
        });

        EditText endTimeDisplay = findViewById(R.id.edit_class_finish_time);
        endTimeDisplay.setText(new SimpleDateFormat("h:mm a").format(endTime.getTime()));
        endTimeDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int endHour = endTime.get(Calendar.HOUR);
                int endMinute = endTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        endTime.set(Calendar.HOUR_OF_DAY, hour);
                        endTime.set(Calendar.MINUTE, minute);
                        Date thisDate = endTime.getTime();
                        endTimeDisplay.setText(new SimpleDateFormat("h:mm a").format(thisDate));
                    }
                }, endHour, endMinute, false);
                timePickerDialog.show();
            }
        });

        picker = (NumberPicker) findViewById(R.id.capacity_picker);
        String[] nums = new String[40];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = Integer.toString(i+1);
        }
        picker.setMinValue(0);
        picker.setMaxValue(39);
        picker.setDisplayedValues(nums);
        picker.setValue(capacity-1);

        Button save = (Button) findViewById(R.id.edit_class_type_save);
        save.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                String typeOfClass = spin.getSelectedItem().toString();
                String instructorName = userAccount.getUsername();
                int classCapacity = picker.getValue()+1;
                long startTimeTime = startTime.getTime().getTime();
                long endTimeTime = endTime.getTime().getTime();

                //Verifying if the class already exists
                String otherInstructor = database.classExists(typeOfClass, startTimeTime);

                if (otherInstructor != null && !otherInstructor.equals(userAccount.getUsername())) {
                    Snackbar.make(findViewById(R.id.create_new_class_type_screen), "Time slot is reserved by " + otherInstructor, Snackbar.LENGTH_SHORT).show();
                } else {
                    if (id == -1) {
                        database.addClass(typeOfClass, instructorName, classCapacity, startTimeTime, endTimeTime);
                    } else {
                        database.updateClass(id, typeOfClass, instructorName, classCapacity, startTimeTime, endTimeTime);
                    }
                    Intent returnIntent = new Intent();
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            }
        });

    }

}