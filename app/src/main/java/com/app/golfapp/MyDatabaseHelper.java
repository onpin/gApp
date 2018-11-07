package com.app.golfapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper  extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "offline_db";


    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(MyDB.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + MyDB.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertData(String latlong) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(MyDB.COLUMN_LATLONG, latlong);

        // insert row
        long id = db.insert(MyDB.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public MyDB getData(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(MyDB.TABLE_NAME,
                new String[]{MyDB.COLUMN_ID, MyDB.COLUMN_LATLONG, MyDB.COLUMN_TIMESTAMP},
                MyDB.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare getLatlong object
        MyDB mynedb = new MyDB(
                cursor.getInt(cursor.getColumnIndex(MyDB.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(MyDB.COLUMN_LATLONG)),
                cursor.getString(cursor.getColumnIndex(MyDB.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return mynedb;
    }

    public List<MyDB> getAllData() {
        List<MyDB> mydblist = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + MyDB.TABLE_NAME + " ORDER BY " +
                MyDB.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MyDB data = new MyDB();
                data.setId(cursor.getInt(cursor.getColumnIndex(MyDB.COLUMN_ID)));
                data.setLatlong(cursor.getString(cursor.getColumnIndex(MyDB.COLUMN_LATLONG)));
                data.setTimestamp(cursor.getString(cursor.getColumnIndex(MyDB.COLUMN_TIMESTAMP)));

                mydblist.add(data);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return getLatlong list
        return mydblist;
    }

    public int getDataCount() {
        String countQuery = "SELECT  * FROM " + MyDB.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateData(MyDB mynewDb) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyDB.COLUMN_LATLONG, mynewDb.getLatlong());

        // updating row
        return db.update(MyDB.TABLE_NAME, values, MyDB.COLUMN_ID + " = ?",
                new String[]{String.valueOf(mynewDb.getId())});
    }

    public void deleteNote(MyDB mynewDb) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MyDB.TABLE_NAME, MyDB.COLUMN_ID + " = ?",
                new String[]{String.valueOf(mynewDb.getId())});
        db.close();
    }

}
