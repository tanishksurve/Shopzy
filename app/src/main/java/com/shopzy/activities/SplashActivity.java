package com.shopzy.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import com.shopzy.R;

public class SplashActivity extends AppCompatActivity {

    private View mainContent, logoContainer, ivLogo, tvAppName, tvTagline, loaderContainer, 
                 ring1, ring2, ambientGlow1, ambientGlow2, flashOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Bind views
        mainContent = findViewById(R.id.mainContent);
        logoContainer = findViewById(R.id.logoContainer);
        ivLogo = findViewById(R.id.ivLogo);
        tvAppName = findViewById(R.id.tvAppName);
        tvTagline = findViewById(R.id.tvTagline);
        loaderContainer = findViewById(R.id.loaderContainer);
        ring1 = findViewById(R.id.loaderRing1);
        ring2 = findViewById(R.id.loaderRing2);
        ambientGlow1 = findViewById(R.id.ambientGlow1);
        ambientGlow2 = findViewById(R.id.ambientGlow2);
        flashOverlay = findViewById(R.id.flashOverlay);

        // Initial states
        logoContainer.setAlpha(0f);
        logoContainer.setScaleX(0.8f);
        logoContainer.setScaleY(0.8f);
        tvAppName.setAlpha(0f);
        tvTagline.setAlpha(0f);
        loaderContainer.setAlpha(0f);
        flashOverlay.setAlpha(0f);

        startAmbientAnimations();
        runPhaseSequence();
    }

    private void startAmbientAnimations() {
        // Subtle slow movement and pulse for background glows
        ObjectAnimator trans1 = ObjectAnimator.ofFloat(ambientGlow1, "translationX", -20f, 20f);
        trans1.setDuration(4000);
        trans1.setRepeatCount(ObjectAnimator.INFINITE);
        trans1.setRepeatMode(ObjectAnimator.REVERSE);
        trans1.start();
        
        ambientGlow1.animate().alpha(0.3f).setDuration(3000).start();
        ambientGlow2.animate().alpha(0.25f).setDuration(4000).start();
    }

    private void runPhaseSequence() {
        // Phase 1: Smooth Entrance (0 - 1.2s)
        logoContainer.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(1000)
                .setInterpolator(new AccelerateDecelerateInterpolator()).start();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            tvAppName.animate().alpha(1f).setDuration(800).start();
            tvTagline.animate().alpha(0.9f).setDuration(800).start();
            loaderContainer.animate().alpha(1f).setDuration(500).start();
            startPulsing(ring1, 0);
            startPulsing(ring2, 1000);
        }, 500);

        // Phase 2: Absorption Effect (1.5s - 2.8s)
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            mainContent.animate().scaleX(0.94f).scaleY(0.94f).alpha(0.85f).setDuration(1200)
                    .setInterpolator(new AccelerateDecelerateInterpolator()).start();
            ivLogo.animate().scaleX(1.1f).scaleY(1.1f).setDuration(1200).start();
        }, 1500);

        // Phase 3: Hero Expansion & White Flash (3.0s - 4.0s)
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            loaderContainer.animate().alpha(0f).setDuration(200).start();
            
            // Hero expansion of the logo
            ivLogo.animate().scaleX(12f).scaleY(12f).alpha(0f).setDuration(800)
                    .setInterpolator(new AccelerateInterpolator()).start();
            
            // Entire content fades out
            mainContent.animate().alpha(0f).scaleX(0.6f).scaleY(0.6f).setDuration(600).start();

            // Flash overlay appears quickly
            flashOverlay.animate().alpha(1f).setDuration(500).setStartDelay(300).start();
        }, 3000);

        // Phase 4: Transition to Home or Login (4s)
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent;
            if (com.shopzy.utils.SessionManager.getInstance(SplashActivity.this).isLoggedIn()) {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, 3800);
    }


    private void startPulsing(View view, int delay) {
        ObjectAnimator pulseX = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1.5f);
        ObjectAnimator pulseY = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1.5f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);

        pulseX.setRepeatCount(ObjectAnimator.INFINITE);
        pulseY.setRepeatCount(ObjectAnimator.INFINITE);
        alpha.setRepeatCount(ObjectAnimator.INFINITE);

        AnimatorSet pulse = new AnimatorSet();
        pulse.playTogether(pulseX, pulseY, alpha);
        pulse.setDuration(2000);
        pulse.setStartDelay(delay);
        pulse.start();
    }
}



