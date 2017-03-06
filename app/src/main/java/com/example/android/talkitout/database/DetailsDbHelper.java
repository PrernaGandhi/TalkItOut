package com.example.android.talkitout.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.talkitout.database.ContractClass.LoginEntry;


/**
 * Created by Prerna on 3/6/2017.
 */

public class DetailsDbHelper  extends SQLiteOpenHelper{

        /**
         * Database version. If you change the database schema, you must increment the database version.
         */
        private static final int DATABASE_VERSION1 = 2;
        /** Name of the database file */
        private static final String DATABASE_NAME1 = "details.db";

        // Constants to construct SQLite statements
        private static final String INTEGER = " INTEGER";
        private static final String TEXT = " TEXT";
        private static final String NOT_NULL = " NOT NULL";
        private static final String AUTOINCREMENT = " PRIMARY KEY AUTOINCREMENT";
        private static final String UNIQUE = " UNIQUE";
        private static final String COMMA_SEP = ", ";

        // Constants for SQLite statements themselves;
        private static final String SQL_SAVE_DETAILS =
                "CREATE TABLE IF NOT EXISTS "+LoginEntry.TABLE_SAVE +" ("+
                        LoginEntry._ID1 + INTEGER + AUTOINCREMENT + COMMA_SEP +
                        LoginEntry.COLUMN_USER_NAME + TEXT + NOT_NULL +COMMA_SEP+
                        LoginEntry.COLUMN_USER_EMAIL1 + TEXT +UNIQUE+ NOT_NULL + COMMA_SEP +
                        LoginEntry.COLUMN_PHONE_NO +INTEGER +NOT_NULL + COMMA_SEP +
                        LoginEntry.COLUMN_DOB + TEXT +NOT_NULL +
                        ");";


        public DetailsDbHelper(Context context) {
            super(context, DATABASE_NAME1, null, DATABASE_VERSION1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_SAVE_DETAILS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


