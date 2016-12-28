package com.software.fire.androidsqlitedatabasetutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.software.fire.androidsqlitedatabasetutorial.database.DbHelper;
import com.software.fire.androidsqlitedatabasetutorial.model.Contact;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DbHelper db = new DbHelper(MainActivity.this);

        Log.d(TAG, "inserting ..");
        db.addContact(new Contact("Randomz", "1234567890"));
        db.addContact(new Contact("Experiment", "2345678901"));
        db.addContact(new Contact("Zmodnar", "0987654321"));
        db.addContact(new Contact("Tnemirepxe", "1098765432"));


        Log.d(TAG, "Reading all contacts ..");
        List<Contact> contacts = db.getAllContacts();
        for (Contact contact : contacts) {
            String log = contact.toString();
            Log.d(TAG, log);
        }
    }
}
