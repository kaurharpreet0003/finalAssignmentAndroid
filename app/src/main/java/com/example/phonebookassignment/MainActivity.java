package com.example.phonebookassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseHelper databaseHelper;
    EditText first_name, last_name, phone_number, user_address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        first_name = findViewById(R.id.firstName);
        last_name = findViewById(R.id.lastName);
        phone_number = findViewById(R.id.phoneNumber);
        user_address = findViewById(R.id.address);

        findViewById(R.id.btnSave).setOnClickListener(this);
        findViewById(R.id.savedInfo).setOnClickListener(this);

        databaseHelper = new DatabaseHelper(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                addPerson();
                break;
            case R.id.savedInfo:
                Intent intent = new Intent(MainActivity.this, PersonActivity.class);
                // start activity to another activity to see the list of employees
//                Intent intent = new Intent(MainActivity.this, PersonActivity.class);
               startActivity(intent);
                break;
        }

    }

    private void addPerson() {
        String firstName = first_name.getText().toString().trim();
        String phoneNmb = phone_number.getText().toString().trim();
        String lastName = last_name.getText().toString().trim();
        String address = user_address.getText().toString().trim();

        if (firstName.isEmpty()) {
            first_name.setError("First name field is mandatory");
            first_name.requestFocus();
            return;
        }

        if (lastName.isEmpty()) {
            last_name.setError("Last name field is mandatory");
            last_name.requestFocus();
            return;
        }
        if (phoneNmb.isEmpty()) {
            phone_number.setError("Phone field cannot be empty");
            phone_number.requestFocus();
            return;
        }
        if (address.isEmpty()) {
            user_address.setError("Address field is mandatory");
            user_address.requestFocus();
            return;
        }

        if (databaseHelper.addPerson(firstName, lastName, address, Integer.parseInt(phoneNmb)))
            Toast.makeText(this, "Information is added", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Information is not added", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        first_name.setText("");
        last_name.setText("");
        user_address.setText("");
        phone_number.setText("");

    }
}
