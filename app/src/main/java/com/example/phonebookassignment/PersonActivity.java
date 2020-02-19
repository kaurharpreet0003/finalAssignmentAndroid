package com.example.phonebookassignment;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PersonActivity extends AppCompatActivity {
    //private static final String TAG = "PersonActivity";
    DatabaseHelper databaseHelper;
    List<PersonClass> personList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        listView = findViewById(R.id.lvPerson);
        personList = new ArrayList<>();

        databaseHelper = new DatabaseHelper(this);
        loadEmployees();
    }

    private void loadEmployees() {

        /*
        String sql = "SELECT * FROM employees";
        Cursor cursor = mDatabase.rawQuery(sql, null);

         */
        Cursor cursor = databaseHelper.getAllPersons();

        if (cursor.moveToFirst()) {
            do {
                personList.add(new PersonClass(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3)

                ));
            } while (cursor.moveToNext());
            cursor.close();

            PersonAdapter personAdapter = new PersonAdapter(this, R.layout.list_person, personList, databaseHelper);
            listView.setAdapter(personAdapter);


        }
    }
}
