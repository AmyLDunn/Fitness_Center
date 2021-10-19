package com.example.fitnesscenter.newaccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fitnesscenter.mainapp.AdminActivity;
import com.example.fitnesscenter.R;
import com.example.fitnesscenter.helper.Account;
import com.example.fitnesscenter.helper.AccountType;
import com.example.fitnesscenter.helper.DBHelper;

public class NewAccountActivity extends AppCompatActivity {

    DBHelper database;

    Spinner accountTypeSpinner;
    int spinnerPosition;

    TextView createErrorMessage;

    Button createNewAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        database = new DBHelper(this);

        createErrorMessage = (TextView) findViewById(R.id.errorMessage);

        accountTypeSpinner = (Spinner) findViewById(R.id.accountTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.accountTypeChoices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeSpinner.setAdapter(adapter);
        spinnerPosition = 0;

        accountTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spinnerPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        createNewAccountBtn = findViewById(R.id.create_account_button);

        createNewAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewAccountDetails();
            }
        });

    }

    private void saveNewAccountDetails(){
        EditText newUsername = (EditText) findViewById(R.id.newUsernameTextView);
        EditText newPassword = (EditText) findViewById(R.id.newPasswordTextView);
        if ( database.accountExists(newUsername.getText().toString()) ) {
            // The account already exists. We cannot create a new one
            createErrorMessage.setText("An account with that name already exists!");
        } else {
            createErrorMessage.setText("");
            AccountType accountType = AccountType.valueOf(spinnerPosition);
            String username = newUsername.getText().toString();
            String password = newPassword.getText().toString();
            Account userAccount = database.addAccount(accountType, username, password);
            Intent intent = new Intent(this, AdminActivity.class);
            intent.putExtra("USER_ACCOUNT", userAccount);
            startActivity(intent);
            finish();
        }
    }
}