package com.shopzy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.shopzy.R;
import com.shopzy.activities.LoginActivity;
import com.shopzy.utils.SessionManager;

public class SettingsFragment extends Fragment {

    private MaterialButton btnThemeToggle, btnSaveProfile, btnLogout;
    private TextInputEditText etName, etEmail;
    private TextView tvThemeLabel;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        sessionManager = SessionManager.getInstance(getContext());

        tvThemeLabel = view.findViewById(R.id.tvThemeLabel);
        btnThemeToggle = view.findViewById(R.id.btnThemeToggle);
        etName = view.findViewById(R.id.etSettingsName);
        etEmail = view.findViewById(R.id.etSettingsEmail);
        btnSaveProfile = view.findViewById(R.id.btnSaveProfile);
        btnLogout = view.findViewById(R.id.btnSettingsLogout);

        // Load Data
        etName.setText(sessionManager.getName());
        etEmail.setText(sessionManager.getEmail());
        updateThemeButtonUI();

        // Theme Toggle
        btnThemeToggle.setOnClickListener(v -> {
            boolean isDark = !sessionManager.isDarkMode();
            sessionManager.setDarkMode(isDark);
            
            if (isDark) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            
            updateThemeButtonUI();
            Toast.makeText(getContext(), "Theme updated successfully", Toast.LENGTH_SHORT).show();
        });

        // Save Profile
        btnSaveProfile.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            sessionManager.setName(name);
            sessionManager.setEmail(email);
            Toast.makeText(getContext(), "Profile updated!", Toast.LENGTH_SHORT).show();
        });

        // Logout
        btnLogout.setOnClickListener(v -> {
            sessionManager.logout();
            Toast.makeText(getContext(), "Logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view;
    }

    private void updateThemeButtonUI() {
        boolean isDark = sessionManager.isDarkMode();
        if (isDark) {
            btnThemeToggle.setText("Switch to Light Mode");
            tvThemeLabel.setText("Dark Mode: ON");
        } else {
            btnThemeToggle.setText("Switch to Dark Mode");
            tvThemeLabel.setText("Dark Mode: OFF");
        }
    }
}
