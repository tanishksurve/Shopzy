package com.shopzy.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.shopzy.R;
import com.shopzy.utils.SessionManager;

import android.widget.ImageView;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView ivProfile;
    private TextInputEditText etName;
    private SessionManager sessionManager;
    private Uri selectedImageUri;

    private final ActivityResultLauncher<String> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    Glide.with(this)
                            .load(uri)
                            .into(ivProfile);
                    ivProfile.setImageTintList(null); // Remove the gray tint
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sessionManager = SessionManager.getInstance(this);

        ivProfile = findViewById(R.id.ivEditProfileImage);
        etName = findViewById(R.id.etEditProfileName);
        MaterialButton btnSave = findViewById(R.id.btnSaveProfileUpdate);
        findViewById(R.id.btnBackEditProfile).setOnClickListener(v -> finish());
        findViewById(R.id.btnChangePhotoPicker).setOnClickListener(v -> imagePickerLauncher.launch("image/*"));

        // Load current data
        etName.setText(sessionManager.getName());
        String savedUri = sessionManager.getProfileImageUri();
        if (!savedUri.isEmpty()) {
            selectedImageUri = Uri.parse(savedUri);
            Glide.with(this)
                    .load(selectedImageUri)
                    .into(ivProfile);
            ivProfile.setImageTintList(null);
        }

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
                return;
            }

            sessionManager.setName(name);
            if (selectedImageUri != null) {
                // In a real app, we might need to copy the image to app-private storage
                // because URIs from system picker can be temporary.
                // For this activity, we'll store the URI string.
                sessionManager.setProfileImageUri(selectedImageUri.toString());
            }

            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
