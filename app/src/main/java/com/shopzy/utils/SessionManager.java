package com.shopzy.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    private static SessionManager instance;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "ShopzySession";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    private static final String KEY_DARK_MODE = "isDarkMode";

    // Constructor
    private SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String email) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing email and name in pref
        editor.putString(KEY_EMAIL, email);
        editor.putString("name", name);

        // commit changes
        editor.commit();
    }

    public void setEmail(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user email
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, "User");
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    /**
     * Clear session details
     * */
    public void logout() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    public String getName() {
        return pref.getString("name", "User");
    }

    public void setName(String name) {
        editor.putString("name", name);
        editor.commit();
    }

    public String getProfileImageUri() {
        return pref.getString("profile_image", "");
    }

    public void setProfileImageUri(String uri) {
        editor.putString("profile_image", uri);
        editor.commit();
    }

    public String getAddress() {
        return pref.getString("address", "");
    }

    public void setAddress(String address) {
        editor.putString("address", address);
        editor.commit();
    }

    public boolean isDarkMode() {
        return pref.getBoolean(KEY_DARK_MODE, false);
    }

    public void setDarkMode(boolean isDarkMode) {
        editor.putBoolean(KEY_DARK_MODE, isDarkMode);
        editor.commit();
    }
}
