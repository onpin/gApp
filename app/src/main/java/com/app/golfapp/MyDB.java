package com.app.golfapp;

public class MyDB {

    public static final String TABLE_NAME = "offlineData";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LATLONG = "latlong";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String latlong;
    private String timestamp;
    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_LATLONG + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public MyDB() {
    }

    public MyDB(int id, String latlong, String timestamp) {
        this.id = id;
        this.latlong = latlong;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}


