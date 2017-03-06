package com.example.android.talkitout;

import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.talkitout.database.ContractClass;
import com.example.android.talkitout.database.DetailsDbHelper;
import com.example.android.talkitout.database.LoginDbHelper;
import com.example.android.talkitout.database.LoginProvider;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class AddProfile extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private Button mSubmit;
    private EditText mName;
    private EditText mEmail;
    private EditText mPhone;
    private EditText mDOB;
    private Uri mUri;
    DetailsDbHelper mDbHelper;
    private static final int EXISTING_LOADER = 3;
    private boolean mPetHasChanged = false;

    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the mPetHasChanged boolean to true.
     */
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mPetHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addprofile);

        mName = (EditText) findViewById(R.id.editText1);
        mEmail = (EditText) findViewById(R.id.editText2);
        mPhone = (EditText) findViewById(R.id.editText3);
        mDOB = (EditText) findViewById(R.id.editText4);
        mSubmit = (Button) findViewById(R.id.submit);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent i = new Intent(AddProfile.this, UserHomePage.class);
                startActivity(i);
                invalidateOptionsMenu();

            }
        });
        Intent intent = getIntent();
        // Determine what the associated URI is, if any.
        mUri = intent.getData();

        if(mUri == null) {
            setTitle("Enter Your Credentials");

        }else{
            setTitle("Edit Profile");
            getLoaderManager().initLoader(EXISTING_LOADER, null, this);

        }
        mName.setOnTouchListener(mTouchListener);
        mEmail.setOnTouchListener(mTouchListener);
        mPhone.setOnTouchListener(mTouchListener);
        mDOB.setOnTouchListener(mTouchListener);
    }

    private boolean saveData() {
       // mDbHelper = new DetailsDbHelper(getBaseContext());
        //SQLiteDatabase db = mDbHelper.getReadableDatabase();


        String name = mName.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String phone_no = mPhone.getText().toString().trim();
        String dob = mDOB.getText().toString().trim();
        ContentValues values = new ContentValues();
        values.put(ContractClass.LoginEntry.COLUMN_USER_NAME,name);
        values.put(ContractClass.LoginEntry.COLUMN_USER_EMAIL1,email);
        values.put(ContractClass.LoginEntry.COLUMN_PHONE_NO,phone_no);
        values.put(ContractClass.LoginEntry.COLUMN_DOB,dob);

        Uri newRowUri = getContentResolver().insert(ContractClass.LoginEntry.CONTENT_URI1, values);

        // If the row URI is null, this means a new row was not successfully inserted into the table
        if (newRowUri == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ContractClass.LoginEntry._ID1,
                ContractClass.LoginEntry.COLUMN_USER_NAME,
                ContractClass.LoginEntry.COLUMN_USER_EMAIL1,
                ContractClass.LoginEntry.COLUMN_PHONE_NO,
                ContractClass.LoginEntry.COLUMN_DOB
        };
        return new CursorLoader(
                this,                       // Parent activity context
                ContractClass.LoginEntry.CONTENT_URI1,             // The content URI of the table being queried
                projection,                 // The columns to be returned
                null,                       // The selection clause
                null,                       // The selection arguments
                null                        // The sort order
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(ContractClass.LoginEntry.COLUMN_USER_NAME));
            String email = cursor.getString(cursor.getColumnIndex(ContractClass.LoginEntry.COLUMN_USER_EMAIL1));
            String phone = cursor.getString(cursor.getColumnIndex(ContractClass.LoginEntry.COLUMN_PHONE_NO));
            String dob = cursor.getString(cursor.getColumnIndex(ContractClass.LoginEntry.COLUMN_DOB));

            // Update the views on the screen with the values from the database
            mName.setText(name);
            mEmail.setText(email);
            mPhone.setText(phone);
            mDOB.setText(dob);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mName.setText("");
        mEmail.setText("");
        mPhone.setText("");
        mDOB.setText("");
    }
}
