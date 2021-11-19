package com.example.fitnesscenter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnesscenter.database.DBHelper;
import com.example.fitnesscenter.helper.Account;
import com.example.fitnesscenter.helper.ScheduledClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CreateNewClassActivity extends AppCompatActivity {

    DBHelper database;

    Account userAccount;

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
            startTime.set(Calendar.HOUR, 12);
            endTime = new GregorianCalendar();
            endTime.set(Calendar.HOUR, 12);
            capacity = 0;
        } else {
            ScheduledClass scheduledClass = database.getClass(id);
            capacity = scheduledClass.getCapacity();
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


        EditText startDateDisplay = findViewById(R.id.edit_class_date);
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
        startTimeDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        startTime.set(Calendar.HOUR_OF_DAY, hour);
                        startTime.set(Calendar.MINUTE, minute);
                        Date thisDate = startTime.getTime();
                        startTimeDisplay.setText(new SimpleDateFormat("h:mm a").format(thisDate));
                    }
                }, 12, 0, false);
                timePickerDialog.show();
            }
        });

        EditText endTimeDisplay = findViewById(R.id.edit_class_finish_time);
        endTimeDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        endTime.set(Calendar.HOUR_OF_DAY, hour);
                        endTime.set(Calendar.MINUTE, minute);
                        Date thisDate = endTime.getTime();
                        endTimeDisplay.setText(new SimpleDateFormat("h:mm a").format(thisDate));
                    }
                }, 12, 0, false);
                timePickerDialog.show();
            }
        });

        picker = findViewById(R.id.capacity_picker);
        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int capacity = picker.getValue();
                picker.getDisplay();
            }
        });

        //TODO: The NumberPicker isn't a "scrolling" type of input, it requires the user
        //      to type in the number. It also doesn't display the input.


        // TODO: Get the save button by using findViewById()

        // TODO: Add an onClickListener to the save button
        //       Collect all the data:
        //            String typeOfClass
        //            String instructorName (you can get this from userAccount.getUsername() )
        //            int capacity
        //            long startTime ( startTime.getTime().getTime() -> The first getTime turns it into
        //                             a Date object and the second getTime turns it into a long. SQLite
        //                             cannot store dates, so we have to do this)
        //            long endTime (same as startTime)
        //       Check if the class exists using
        //       String otherInstructor = database.classExists(String type, long startTime)
        //       If the class exists, this method will return the name of the instructor that is teaching it
        //            Make a Snackbar that displays the name of the instructor
        //       If the class does not exist:
        //            If the id (from the top) is -1, call
        //                 database.addClass(String type, String instructor, int capacity, long startTime, long endTime)
        //            If the id if not -1, call
        //                 database.updateClass(int id, String type, String instructor, int capacity, long startTime, long endTime)
        //            Regardless of the above, tell the original page that a class was added/updated
        //                 Intent returnIntent = new Intent();
        //                 setResult(RESULT_OK, returnIntent);
        //                 finish();


    }

}