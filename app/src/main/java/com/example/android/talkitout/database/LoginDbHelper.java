package com.example.android.talkitout.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.talkitout.database.ContractClass.LoginEntry;

import static android.R.attr.version;

/**
 * Created by Prerna on 3/4/2017.
 */

public class LoginDbHelper extends SQLiteOpenHelper {

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;
    /** Name of the database file */
    private static final String DATABASE_NAME = "login.db";

    // Constants to construct SQLite statements
    private static final String INTEGER = " INTEGER";
    private static final String TEXT = " TEXT";
    private static final String NOT_NULL = " NOT NULL";
    private static final String AUTOINCREMENT = " PRIMARY KEY AUTOINCREMENT";
    private static final String UNIQUE = " UNIQUE";
    private static final String COMMA_SEP = ", ";

    // Constants for SQLite statements themselves;
    private static final String SQL_CREATE_LOGIN_TABLE =
            "CREATE TABLE " + LoginEntry.TABLE_NAME  + " (" +
                    LoginEntry._ID + INTEGER + AUTOINCREMENT + COMMA_SEP +
                    LoginEntry.COLUMN_USER_NAME + TEXT +UNIQUE+ NOT_NULL + COMMA_SEP +
                    LoginEntry.COLUMN_USER_PASSWORD + TEXT + NOT_NULL +
                    ");";


    public LoginDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
