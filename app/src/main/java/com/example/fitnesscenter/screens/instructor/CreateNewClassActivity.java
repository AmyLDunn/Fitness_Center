package com.example.fitnesscenter.screens.instructor;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnesscenter.R;
import com.example.fitnesscenter.database.DBHelper;
import com.example.fitnesscenter.database.SharedPreferencesManager;
import com.example.fitnesscenter.helper.ScheduledClass;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateNewClassActivity extends AppCompatActivity {

    DBHelper database;
    String username;

    String type;
    Calendar startTime;
    Calendar endTime;
    NumberPicker picker;
    int capacity;
    String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_class);

        database = new DBHelper(this);
        SharedPreferencesManager SP = new SharedPreferencesManager(this);
        username = SP.getUsername();

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("CLASS_ID");

        if ( id == -1 ){ // Making a new scheduled class
            startTime = Calendar.getInstance();
            endTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR, 12);
            startTime.set(Calendar.MINUTE, 0);
            endTime.set(Calendar.HOUR, 12);
            endTime.set(Calendar.MINUTE, 0);
            capacity = 10;
            difficulty = "Beginner";
        } else {
            ScheduledClass scheduledClass = database.getClass(id);
            type = scheduledClass.getType();
            capacity = scheduledClass.getCapacity();
            startTime = scheduledClass.getStartTime();
            endTime = scheduledClass.getEndTime();
            difficulty = scheduledClass.getDifficulty();
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
        if (id != -1) {
            spin.setSelection(spinnerArrayAdapter.getPosition(type));
        }


        EditText startDateDisplay = findViewById(R.id.edit_class_date);
        startDateDisplay.setText(new SimpleDateFormat("MMMM d, yyyy").format(startTime.getTime()));
        startDateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = startTime.get(Calendar.YEAR);
                int month = startTime.get(Calendar.MONTH);
                int day = startTime.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        startTime.set(Calendar.YEAR, year);
                        startTime.set(Calendar.MONTH, month);
                        startTime.set(Calendar.DAY_OF_MONTH, day);
                        endTime.set(Calendar.YEAR, year);
                        endTime.set(Calendar.MONTH, month);
                        endTime.set(Calendar.DAY_OF_MONTH, day);
                        Date thisDate = startTime.getTime();
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

        Spinner difficultyPicker = (Spinner) findViewById(R.id.select_class_difficulty);
        ArrayList<String> classDifficulties = new ArrayList<>();
        classDifficulties.add("Beginner");
        classDifficulties.add("Intermediate");
        classDifficulties.add("Advanced");
        ArrayAdapter<String> difficultyArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classDifficulties);
        difficultyArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultyPicker.setAdapter(difficultyArrayAdapter);
        difficultyPicker.setSelection(spinnerArrayAdapter.getPosition(difficulty));

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
                int classCapacity = picker.getValue()+1;
                long startTimeTime = startTime.getTime().getTime();
                long endTimeTime = endTime.getTime().getTime();
                if ( startTimeTime >= endTimeTime ){
                    Snackbar.make(view, "The start time must be before the end time", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                difficulty = difficultyPicker.getSelectedItem().toString();

                //Verifying if the class already exists
                String otherInstructor = database.classExists(typeOfClass, startTimeTime);

                if (otherInstructor != null && !otherInstructor.equals(username)) {
                    Snackbar.make(findViewById(R.id.create_new_class_type_screen), "Class type already scheduled by " + otherInstructor, Snackbar.LENGTH_SHORT).show();
                } else {
                    if (id == -1) {
                        database.addClass(typeOfClass, username, classCapacity, startTimeTime, endTimeTime, startTime.get(Calendar.DAY_OF_WEEK), difficulty);
                    } else {
                        database.updateClass(id, typeOfClass, username, classCapacity, startTimeTime, endTimeTime, startTime.get(Calendar.DAY_OF_WEEK), difficulty);
                    }
                    Intent returnIntent = new Intent();
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            }
        });

    }

}