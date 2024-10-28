package com.example.locationfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "location.db";
    private static final String TABLE_NAME = "locations";
    private static final String COL_ID = "id";
    private static final String COL_ADDRESS = "address";
    private static final String COL_LATITUDE = "latitude";
    private static final String COL_LONGITUDE = "longitude";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            COL_ADDRESS + " TEXT, " +
                            COL_LATITUDE + " REAL, " +
                            COL_LONGITUDE + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addLocation(String address, double latitude, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ADDRESS, address);
        values.put(COL_LATITUDE, latitude);
        values.put(COL_LONGITUDE, longitude);

        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public Cursor getLocation(String address) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ADDRESS + " = ?", new String[]{address});
    }

    public boolean updateLocation(String address, double latitude, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_LATITUDE, latitude);
        values.put(COL_LONGITUDE, longitude);

        int result = db.update(TABLE_NAME, values, COL_ADDRESS + " = ?", new String[]{address});
        return result > 0;
    }

    public boolean deleteLocation(String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COL_ADDRESS + " = ?", new String[]{address});
        return result > 0;
    }
}
