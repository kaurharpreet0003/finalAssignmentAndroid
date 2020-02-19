package com.example.phonebookassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PersonDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Person";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_LAST_NAME = "lastName";
    private static final String COLUMN_ADDRESS = "address";
        private static final String COLUMN_PHONE = "phone";

    public DatabaseHelper(@Nullable Context context) {
        // cursor factory is when you are using your own custom cursor
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER NOT NULL CONSTRAINT person_pk PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRST_NAME + " varchar(200) NOT NULL, " +
                COLUMN_LAST_NAME + " varchar(200) NOT NULL, " +
                COLUMN_ADDRESS + " varchar(200) NOT NULL, " +
                COLUMN_PHONE + " int NOT NULL);";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
         * we are just dropping the table and recreate it
         * */

        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }

    boolean addPerson(String first_name, String last_name, String address, int phone) {

        // in order to insert items into database, we need a writable database
        // this method returns a SQLite database instance
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // we need to define a contentValues instance
        ContentValues cv = new ContentValues();

        // the first argument of the put method is the column name and the second the value
        cv.put(COLUMN_FIRST_NAME, first_name);
        cv.put(COLUMN_LAST_NAME, last_name);
        cv.put(COLUMN_ADDRESS, address);
        cv.put(COLUMN_PHONE, String.valueOf(phone));

        // the insert method returns row number if the insertion is successful and -1 if unsuccessful
        return sqLiteDatabase.insert(TABLE_NAME, null, cv) != -1;
//        return true;
    }

    Cursor getAllPersons() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    boolean updatePerson(int id, String firstName, String lastName, String address, int phone) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRST_NAME, firstName);
        cv.put(COLUMN_LAST_NAME, lastName);
        cv.put(COLUMN_ADDRESS, address);
        cv.put(COLUMN_PHONE, String.valueOf(phone));

        // this method returns the number of rows affected
        return sqLiteDatabase.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    boolean deletePerson(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        // the delete method returns the number of rows affected
        return sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }


}
