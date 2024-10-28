package com.example.locationfinder;

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

    DatabaseHelper dbHelper;
    EditText addressInput, latitudeInput, longitudeInput;

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

        dbHelper = new DatabaseHelper(this);
        addressInput = findViewById(R.id.addressInput);
        latitudeInput = findViewById(R.id.latitudeInput);
        longitudeInput = findViewById(R.id.longitudeInput);

        Button addButton = findViewById(R.id.addButton);
        Button queryButton = findViewById(R.id.queryButton);
        Button updateButton = findViewById(R.id.updateButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        addButton.setOnClickListener(v -> {
            String address = addressInput.getText().toString();
            double latitude = Double.parseDouble(latitudeInput.getText().toString());
            double longitude = Double.parseDouble(longitudeInput.getText().toString());

            dbHelper.addLocation(address, latitude, longitude);
            showToast("Location added successfully.");
        });

        queryButton.setOnClickListener(v -> {
            String address = addressInput.getText().toString();
            Cursor cursor = dbHelper.getLocation(address);

            if (cursor.moveToFirst()) {
                double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                showToast("Latitude: " + latitude + "\nLongitude: " + longitude);
            } else {
                showToast("Location not found.");
            }
            cursor.close();
        });

        updateButton.setOnClickListener(v -> {
            String address = addressInput.getText().toString();
            double latitude = Double.parseDouble(latitudeInput.getText().toString());
            double longitude = Double.parseDouble(longitudeInput.getText().toString());

            dbHelper.updateLocation(address, latitude, longitude);
            showToast("Location updated successfully.");
        });

        deleteButton.setOnClickListener(v -> {
            String address = addressInput.getText().toString();
            dbHelper.deleteLocation(address);
            showToast("Location deleted successfully.");
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}