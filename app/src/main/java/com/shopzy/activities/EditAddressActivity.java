package com.shopzy.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shopzy.R;
import com.shopzy.utils.SessionManager;
import com.google.android.material.textfield.TextInputEditText;

public class EditAddressActivity extends AppCompatActivity {

    private TextInputEditText etArea, etCity, etPincode;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
 
        etArea = findViewById(R.id.etArea);
        etCity = findViewById(R.id.etCity);
        etPincode = findViewById(R.id.etPincode);

        // Load existing address if available
        String savedAddress = SessionManager.getInstance(this).getAddress();
        if (!savedAddress.isEmpty()) {
            // Format: Area\nCity, Pincode
            String[] parts = savedAddress.split("\n");
            if (parts.length >= 2) {
                etArea.setText(parts[0]);
                String[] cityPin = parts[1].split(", ");
                if (cityPin.length >= 2) {
                    etCity.setText(cityPin[0]);
                    etPincode.setText(cityPin[1]);
                } else {
                    etCity.setText(parts[1]);
                }
            } else {
                etArea.setText(savedAddress);
            }
        }
 
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
 
        findViewById(R.id.btnSaveAddress).setOnClickListener(v -> {
            String area = etArea.getText().toString().trim();
            String city = etCity.getText().toString().trim();
            String pincode = etPincode.getText().toString().trim();
 
            if (area.isEmpty() || city.isEmpty() || pincode.isEmpty()) {
                android.widget.Toast.makeText(this, "Please fill all fields", android.widget.Toast.LENGTH_SHORT).show();
                return;
            }
 
            String fullAddress = area + "\n" + city + ", " + pincode;
            com.shopzy.utils.SessionManager.getInstance(this).setAddress(fullAddress);
 
            android.widget.Toast.makeText(this, "Address updated successfully!", android.widget.Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
