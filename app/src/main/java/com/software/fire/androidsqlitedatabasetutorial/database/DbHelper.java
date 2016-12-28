package com.software.fire.androidsqlitedatabasetutorial.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.software.fire.androidsqlitedatabasetutorial.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brad on 12/28/2016.
 */

public class DbHelper extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "contactsManager";

    //Contacts Table Name
    private static final String TABLE_CONTACTS = "contacts";

    //Contacts Table Columns Names
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE_NO = "phone_number";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, "
                + KEY_PHONE_NO + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    //Adding new contact
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE_NO, contact.getPhoneNumber());

        //Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public Contact getContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(
                TABLE_CONTACTS,
                new String[]{
                        KEY_ID,
                        KEY_NAME,
                        KEY_PHONE_NO
                },
                KEY_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null, null
        );

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Contact contact = new Contact();
        contact.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        contact.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
        contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(KEY_PHONE_NO)));

        cursor.close();
        return contact;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        //Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                contact.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(KEY_PHONE_NO)));

                //Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return contactList;
    }

    public int getContactCount() {
        String countQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE_NO, contact.getPhoneNumber());

        //updating row
        int returnUpdate = db.update(
                TABLE_CONTACTS,
                values,
                KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getId())}
        );
        db.close();
        return returnUpdate;
    }

    public int deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        int returnDelete = db.delete(
                TABLE_CONTACTS,
                KEY_ID + "11 = ?",
                new String[]{String.valueOf(contact.getId())}
        );
        db.close();
        return returnDelete;
    }
}
