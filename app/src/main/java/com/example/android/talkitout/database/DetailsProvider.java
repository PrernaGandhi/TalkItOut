package com.example.android.talkitout.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Prerna on 3/6/2017.
 */

public class DetailsProvider extends ContentProvider {

    public static final int LOGIN = 100;
    public static final int LOGIN_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {

        sUriMatcher.addURI(ContractClass.CONTENT_AUTHORITY1, ContractClass.PATH1, LOGIN);


        sUriMatcher.addURI(ContractClass.CONTENT_AUTHORITY1, ContractClass.PATH1 + "/#", LOGIN_ID);
    }

    private DetailsDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new DetailsDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
// Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case LOGIN:

                cursor = database.query(ContractClass.LoginEntry.TABLE_SAVE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case LOGIN_ID:

                selection = ContractClass.LoginEntry._ID1 + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(ContractClass.LoginEntry.TABLE_SAVE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // Set notification URI on the Cursor, so we know what content URI the
        // Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;    }

    @Nullable
    @Override
    public String getType(Uri uri) {
// Get the integer code produced by the URI matcher
        final int match = sUriMatcher.match(uri);
        switch (match) {
            // Return the MIME type for the whole directory, aka ALL the rows of the pets table
            case LOGIN:
                return ContractClass.LoginEntry.CONTENT_LIST_TYPE1;
            // Return the MIME type for a single item, aka a single row of the pets table
            case LOGIN_ID:
                return ContractClass.LoginEntry.CONTENT_ITEM_TYPE1;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case LOGIN:
                return insertUser(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }    }

    private Uri insertUser(Uri uri, ContentValues values) {

        String name = values.getAsString(ContractClass.LoginEntry.COLUMN_USER_NAME);
        if (name == null ) {
            throw new IllegalArgumentException("User requires a name");
        }
        String email = values.getAsString(ContractClass.LoginEntry.COLUMN_USER_EMAIL1);

        String phone = values.getAsString(ContractClass.LoginEntry.COLUMN_PHONE_NO);
        if (phone == null ) {
            throw new IllegalArgumentException("User requires a phone");
        }
        String dob = values.getAsString(ContractClass.LoginEntry.COLUMN_DOB);
        if (dob == null ) {
            throw new IllegalArgumentException("User requires a D.O.B");
        }


        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(ContractClass.LoginEntry.TABLE_SAVE, null, values);

        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            return null;
        }

        // Since the ID was not -1, insertion into the pets table was successful.
        // Notify all listeners that the content URI has changed, and the current Cursor is stale.
        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Get the integer code produced by the URI matcher
        final int match = sUriMatcher.match(uri);

        int rowsDeleted;

        // Some integer codes, like LOGIN and LOGIN_ID are cases that mean a valid URI pattern was
        // matched, and can be used to delete rows from the pets table
        switch(match) {
            // Delete all rows that match the selection and selection args
            case LOGIN:
                rowsDeleted = database.delete(ContractClass.LoginEntry.TABLE_SAVE, selection, selectionArgs);
                break;
            // Delete a single row given by the ID in the URI
            case LOGIN_ID:
                // For the LOGIN_ID code, extract out the ID from the URI,
                // so we know which row to delete. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID passed in with the URI.
                selection = ContractClass.LoginEntry._ID1 + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(ContractClass.LoginEntry.TABLE_SAVE, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If rows were deleted, notify all listeners that the content URI has changed, and the
        // current Cursor is stale.
        if (rowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Returns the number of database rows affected by the deletion statement.
        return rowsDeleted;    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case LOGIN:
                return updateUser(uri, values, selection, selectionArgs);
            case LOGIN_ID:
                selection = ContractClass.LoginEntry._ID1 + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateUser(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }    }

    private int updateUser(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.size() == 0) {
            return 0;
        }

        // INPUT VALIDATION
        // Check if the user is attempting to update the "name" column for X number of rows
        if (values.containsKey(ContractClass.LoginEntry.COLUMN_USER_EMAIL1)) {
            // Check that the user's desired value for the email is not null.
            String email = values.getAsString(ContractClass.LoginEntry.COLUMN_USER_EMAIL1);
            if (email == null) {
                throw new IllegalArgumentException("User requires a email");
            }
        }

        if (values.containsKey(ContractClass.LoginEntry.COLUMN_USER_NAME)) {
            String name = values.getAsString(ContractClass.LoginEntry.COLUMN_USER_NAME);
            if (name == null ) {
                throw new IllegalArgumentException("User requires valid name");
            }
        }
        if (values.containsKey(ContractClass.LoginEntry.COLUMN_PHONE_NO)) {
            Integer phone_no = values.getAsInteger(ContractClass.LoginEntry.COLUMN_PHONE_NO);
            if (phone_no == null ) {
                throw new IllegalArgumentException("User requires valid phone_no");
            }
        }
        if (values.containsKey(ContractClass.LoginEntry.COLUMN_DOB)) {
            String dob = values.getAsString(ContractClass.LoginEntry.COLUMN_DOB);
            if (dob == null ) {
                throw new IllegalArgumentException("User requires valid dob");
            }
        }



        // Get writable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Update the database with the given values.
        int rowsUpdated = database.update(ContractClass.LoginEntry.TABLE_SAVE, values, selection, selectionArgs);

        // If rows were updated, notify all listeners that the content URI has changed,
        // and the current Cursor is stale.
        if (rowsUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Returns the number of database rows affected by the update statement.
        return rowsUpdated;
    }
}
