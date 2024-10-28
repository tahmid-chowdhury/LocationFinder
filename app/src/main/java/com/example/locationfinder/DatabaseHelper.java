package com.example.locationfinder;

// Imported classes and packages
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Constants for database and table details
    private static final String DATABASE_NAME = "location.db";
    private static final String TABLE_NAME = "locations";
    private static final String COL_ID = "id";
    private static final String COL_ADDRESS = "address";
    private static final String COL_LATITUDE = "latitude";
    private static final String COL_LONGITUDE = "longitude";

    // Constructor to initialize the database
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the locations table with columns
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            COL_ADDRESS + " TEXT, " +
                            COL_LATITUDE + " REAL, " +
                            COL_LONGITUDE + " REAL)";
        db.execSQL(createTable); // Execute the SQL statement to create the table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing table and create a new one when the database is upgraded
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db); // Call the onCreate method to recreate the table
    }

    // Method to add and retrieve locations from the database
    public boolean addLocation(String address, double latitude, double longitude) {
        // Insert a new location into the database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ADDRESS, address);
        values.put(COL_LATITUDE, latitude);
        values.put(COL_LONGITUDE, longitude);

        // Insert the values into the database and check if the insertion was successful
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1; // Return true if the insertion was successful, false otherwise
    }

    // Method to retrieve a location from the database based on its address
    public Cursor getLocation(String address) {
        // Retrieve a location from the database based on its address
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ADDRESS + " = ?", new String[]{address}); // Execute a query to retrieve the location
    }

    // Method to update the location in the database
    public boolean updateLocation(String address, double latitude, double longitude) {
        // Update the location in the database based on its address
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_LATITUDE, latitude);
        values.put(COL_LONGITUDE, longitude);

        // Update the values in the database and check if the update was successful
        int result = db.update(TABLE_NAME, values, COL_ADDRESS + " = ?", new String[]{address});
        return result > 0; // Return true if the update was successful, false otherwise
    }

    // Method to delete a location from the database based on its address
    public boolean deleteLocation(String address) {
        // Delete a location from the database based on its address
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete the location from the database and check if the deletion was successful
        int result = db.delete(TABLE_NAME, COL_ADDRESS + " = ?", new String[]{address});
        return result > 0; // Return true if the deletion was successful, false otherwise
    }
}
