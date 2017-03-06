package com.example.android.talkitout.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Prerna on 3/4/2017.
 */

public final class ContractClass {
    private ContractClass(){}
    public static final String CONTENT_AUTHORITY = "com.example.android.talkitout.database.LoginProvider";
    public static final String CONTENT_AUTHORITY1 = "com.example.android.talkitout.database.DetailsProvider";
    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final Uri BASE_CONTENT_URI1 = Uri.parse("content://" + CONTENT_AUTHORITY1);


    /**
     * Possible path (appended to base content URI for possible URI's)
     */
    public static final String PATH = "login";
    public static final String PATH1 = "save";

    /**
     * This class simply defines the constants for the database, so Contract objects should not
     * be instantiated. Thus it has a private access modifier.
     */

    /* Defines the 'login' table and its column names */
    public static final class LoginEntry implements BaseColumns {

        /** The content URI to access the user data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH);
        public static final Uri CONTENT_URI1 = Uri.withAppendedPath(BASE_CONTENT_URI1, PATH1);


        /** The MIME type of the {@link #CONTENT_URI} for a list of users. */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
        public static final String CONTENT_LIST_TYPE1 =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY1 + "/" + PATH1;

        /** The MIME type of the {@link #CONTENT_URI} for a single user. */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
        public static final String CONTENT_ITEM_TYPE1 =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY1 + "/" + PATH1;


        /** Name of database table for users */
        public final static String TABLE_NAME = "login";
        public final static String TABLE_SAVE="details";

        /**
         * Unique ID number for the user (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;
        public final static String _ID1 = BaseColumns._ID;

        /**
         * Name of the user.
         *
         * Type: TEXT
         */
        public final static String COLUMN_USER_EMAIL ="email";
        public final static String COLUMN_USER_EMAIL1 = "email1";

        /**
         * Password of the user.
         *
         * Type: TEXT
         */
        public final static String COLUMN_USER_PASSWORD = "password";

        public final static String COLUMN_USER_NAME="user_name";
        public final static String COLUMN_PHONE_NO = "phone";
        public final static String COLUMN_DOB= "dob";


    }
}
