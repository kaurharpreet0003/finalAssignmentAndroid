package com.example.phonebookassignment;

import android.app.AlertDialog;
import android.app.Person;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

public class PersonAdapter extends ArrayAdapter {
    Context context;
    int layoutRes;
    List<PersonClass> persons;
    //    SQLiteDatabase mDatabase;
    DatabaseHelper databaseHelper;

    public PersonAdapter(Context context, int layoutRes, List<PersonClass> persons, DatabaseHelper databaseHelper) {
        super(context, layoutRes, persons);
        this.context = context;
        this.layoutRes = layoutRes;
        this.persons = persons;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(layoutRes, null);
        TextView tvFName = v.findViewById(R.id.tv_fname);
        TextView tvPhone = v.findViewById(R.id.tv_phone);
        TextView tvLName = v.findViewById(R.id.tv_lname);
        TextView tvAddress = v.findViewById(R.id.tv_address);

        final PersonClass person = persons.get(position);
        tvFName.setText(person.getFirst_name());
        tvPhone.setText(String.valueOf(person.getPhone()));
        tvAddress.setText(person.getAddress());
        tvLName.setText(person.getLast_name());

        v.findViewById(R.id.btn_edit_person).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePerson(person);
            }
        });

        v.findViewById(R.id.btn_delete_person).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePerson(person);
            }
        });

        return v;
    }

    private void deletePerson(final PersonClass person) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (databaseHelper.deletePerson(person.getId()))
                    loadPerson();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void updatePerson(final PersonClass person) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.update_person, null);
        alert.setView(v);
        final AlertDialog alertDialog = alert.create();
        alertDialog.show();

        final EditText etFirstName = v.findViewById(R.id.editFirstName);
        final EditText etLastName = v.findViewById(R.id.editLastName);
        final EditText etAddress = v.findViewById(R.id.address);
        final EditText etPhone = v.findViewById(R.id.phoneNumber);


        etFirstName.setText(person.getFirst_name());
        etLastName.setText(person.getLast_name());
        etAddress.setText(person.getAddress());
        etPhone.setText(String.valueOf(person.getPhone()));

        v.findViewById(R.id.btn_update_information).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = etFirstName.getText().toString().trim();
                String lastname = etLastName.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String address = etAddress.getText().toString();

                if (firstname.isEmpty()) {
                    etFirstName.setError("First Name field is mandatory!");
                    etFirstName.requestFocus();
                    return;
                }
                if (lastname.isEmpty()) {
                    etLastName.setError("Last Name field is mandatory!");
                    etLastName.requestFocus();
                    return;
                }
                if (address.isEmpty()) {
                    etAddress.setError("Address field is mandatory!");
                    etAddress.requestFocus();
                    return;
                }

                if (phone.isEmpty()) {
                    etPhone.setError("Phone field is mandatory!");
                    etPhone.requestFocus();
                    return;
                }
//
//                if (databaseHelper.updatePerson(person.getId(), firstname, lastname,address, phone)) {
//                    Toast.makeText(context,"Information is updated", Toast.LENGTH_SHORT).show();
//                    loadPerson();
//                } else
//                    Toast.makeText(context,"Information is not updated", Toast.LENGTH_SHORT).show();
//
//                alertDialog.dismiss();
            }
        });
    }

    private void loadPerson() {

        Cursor cursor = databaseHelper.getAllPersons();

        persons.clear();
        if (cursor.moveToFirst()) {

            do {
                persons.add(new PersonClass(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3)
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }

        notifyDataSetChanged();
    }
}
