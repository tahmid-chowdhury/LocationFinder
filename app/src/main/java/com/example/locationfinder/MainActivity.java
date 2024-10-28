package com.example.locationfinder;

// Imported classes and packages
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Declared variables
    DatabaseHelper dbHelper; // Database helper object
    EditText addressInput, latitudeInput, longitudeInput; // Edit text fields

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        dbHelper = new DatabaseHelper(this);
        addressInput = findViewById(R.id.addressInput);
        latitudeInput = findViewById(R.id.latitudeInput);
        longitudeInput = findViewById(R.id.longitudeInput);

        // Initialize buttons
        Button addButton = findViewById(R.id.addButton);
        Button queryButton = findViewById(R.id.queryButton);
        Button updateButton = findViewById(R.id.updateButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        // Set a click listener for the add button
        addButton.setOnClickListener(v -> {
            // Get input values
            String address = addressInput.getText().toString();
            double latitude = Double.parseDouble(latitudeInput.getText().toString());
            double longitude = Double.parseDouble(longitudeInput.getText().toString());

            // Add location to the database
            dbHelper.addLocation(address, latitude, longitude);
            showToast("Location added successfully."); // Show a toast message
        });

        // Set a click listener for the query button
        queryButton.setOnClickListener(v -> {
            // Get input value
            String address = addressInput.getText().toString();
            Cursor cursor = dbHelper.getLocation(address);

            // Check if location exists in the database
            if (cursor.moveToFirst()) {
                // Get latitude and longitude values
                double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                showToast("Latitude: " + latitude + "\nLongitude: " + longitude); // Show a toast message
            } else {
                showToast("Location not found."); // Show a toast message
            }
            cursor.close();
        });

        // Set a click listener for the update button
        updateButton.setOnClickListener(v -> {
            // Get input values
            String address = addressInput.getText().toString();
            double latitude = Double.parseDouble(latitudeInput.getText().toString());
            double longitude = Double.parseDouble(longitudeInput.getText().toString());

            // Update location in the database
            dbHelper.updateLocation(address, latitude, longitude);
            showToast("Location updated successfully."); // Show a toast message
        });

        // Set a click listener for the delete button
        deleteButton.setOnClickListener(v -> {
            // Get input value
            String address = addressInput.getText().toString();

            // Delete location from the database
            dbHelper.deleteLocation(address);
            showToast("Location deleted successfully."); // Show a toast message
        });
    }

    // Method to show a toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}