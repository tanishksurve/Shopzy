package com.shopzy;

import android.app.Application;
import androidx.appcompat.app.AppCompatDelegate;
import com.shopzy.utils.SessionManager;

public class ShopzyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Apply theme globally completely avoiding MainActivity recreations double-loading UI.
        int targetMode = SessionManager.getInstance(this).isDarkMode() ? 
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        
        AppCompatDelegate.setDefaultNightMode(targetMode);
    }
}
