package com.shopzy.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.shopzy.R;
import com.shopzy.utils.RewardManager;
import com.shopzy.views.SpinningWheelView;

import java.util.Random;

public class SpinWinDialogFragment extends DialogFragment {

    private SpinningWheelView wheelView;
    private Button btnSpin;
    private ImageView btnClose;
    private boolean isSpinning = false;

    public static SpinWinDialogFragment newInstance() {
        return new SpinWinDialogFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_spin_win, container, false);
        
        wheelView = view.findViewById(R.id.wheelView);
        btnSpin = view.findViewById(R.id.btnSpinNow);
        btnClose = view.findViewById(R.id.btnCloseSpin);
        
        btnClose.setOnClickListener(v -> dismiss());
        
        btnSpin.setOnClickListener(v -> {
            if (!isSpinning) {
                if (RewardManager.getInstance(requireContext()).canSpinToday()) {
                    startSpin();
                } else {
                    Toast.makeText(getContext(), "You have already used your spin today. Come back tomorrow!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void startSpin() {
        isSpinning = true;
        btnSpin.setEnabled(false);
        
        Random random = new Random();
        float randomDegrees = 360f * (5 + random.nextInt(5)) + random.nextFloat() * 360f;
        
        ObjectAnimator animator = ObjectAnimator.ofFloat(wheelView, "rotationAngle", 0, randomDegrees);
        animator.setDuration(3000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            wheelView.setRotationAngle(value);
        });
        
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isSpinning = false;
                processWin(randomDegrees);
            }
        });
        
        animator.start();
    }

    private void processWin(float finalAngle) {
        String result = wheelView.getRewardAtAngle(finalAngle);
        
        if (result.equals("Better Luck")) {
            showResultDialog("Oops!", "Better luck next time! Don't give up!");
        } else {
            // Map result to RewardManager types
            String type = "";
            double value = 0;
            
            if (result.equals("10% OFF")) {
                type = "PERCENT";
                value = 10.0;
            } else if (result.equals("20% OFF")) {
                type = "PERCENT";
                value = 20.0;
            } else if (result.equals("₹100 Cashback")) {
                type = "FLAT";
                value = 100.0;
            } else if (result.equals("Free Delivery")) {
                type = "FREE_DELIVERY";
                value = 1.0; // 1 means true
            }
            
            RewardManager.getInstance(requireContext()).saveReward(type, value);
            showResultDialog("Congratulations!", "You won " + result + " 🎉. Use it on your next order!");
        }
    }

    private void showResultDialog(String title, String message) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
        builder.setTitle(title)
               .setMessage(message)
               .setPositiveButton("AWESOME", (dialog, which) -> dismiss())
               .setCancelable(false)
               .show();
    }
}
