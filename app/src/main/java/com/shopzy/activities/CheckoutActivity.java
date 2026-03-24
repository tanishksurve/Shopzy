package com.shopzy.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.shopzy.R;
import com.shopzy.models.CartItem;
import com.shopzy.utils.CartManager;
import com.shopzy.utils.RewardManager;

import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        findViewById(R.id.btnBackCheckout).setOnClickListener(v -> finish());

        LinearLayout itemsContainer = findViewById(R.id.orderSummaryItems);
        TextView tvTotal = findViewById(R.id.tvCheckoutTotal);
        TextView tvDisplayAddress = findViewById(R.id.tvDisplayAddress);
        Button btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        android.widget.RadioGroup rgSlot = findViewById(R.id.rgDeliverySlot);
        com.google.android.material.textfield.TextInputEditText etInstructions = findViewById(R.id.etDeliveryInstructions);

        com.shopzy.utils.SessionManager session = com.shopzy.utils.SessionManager.getInstance(this);
        String savedAddress = session.getAddress();
        if (savedAddress.isEmpty()) {
            tvDisplayAddress.setText("No address saved in profile. Please edit your profile to add an address.");
            tvDisplayAddress.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        } else {
            tvDisplayAddress.setText(savedAddress);
            tvDisplayAddress.setTextColor(getResources().getColor(R.color.black));
        }

        CartManager cartManager = CartManager.getInstance(this);
        double subtotal = cartManager.getTotalPrice();
        double deliveryCharge = 10.0;

        // Reward logic
        double savings = 0;
        RewardManager.Reward reward = RewardManager.getInstance(this).getUnusedReward();
        if (reward != null) {
            View cardReward = findViewById(R.id.cardRewardCheckout);
            TextView tvRewardName = findViewById(R.id.tvRewardName);
            TextView tvRewardSavings = findViewById(R.id.tvRewardSavings);
            TextView tvOriginalTotal = findViewById(R.id.tvOriginalTotal);

            cardReward.setVisibility(View.VISIBLE);
            tvRewardName.setText(reward.getDisplayString() + " Applied");

            switch (reward.type) {
                case "PERCENT":
                    savings = subtotal * (reward.value / 100.0);
                    break;
                case "FLAT":
                    savings = reward.value;
                    break;
                case "FREE_DELIVERY":
                    savings = deliveryCharge;
                    break;
            }

            tvRewardSavings.setText("-₹" + String.format(Locale.getDefault(), "%.2f", savings));
            tvOriginalTotal.setVisibility(View.VISIBLE);
            tvOriginalTotal.setText("₹" + String.format(Locale.getDefault(), "%.2f", subtotal + 10.0));
            tvOriginalTotal.setPaintFlags(tvOriginalTotal.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
        }

        final double total = subtotal + deliveryCharge - savings;
        tvTotal.setText(String.format(Locale.getDefault(), "₹%.2f", total));

        // Populate summary
        for (CartItem item : cartManager.getCartItems()) {
            View itemView = LayoutInflater.from(this).inflate(android.R.layout.simple_list_item_2, itemsContainer, false);
            TextView text1 = itemView.findViewById(android.R.id.text1);
            TextView text2 = itemView.findViewById(android.R.id.text2);
            
            text1.setText(item.getProduct().getName() + " (x" + item.getQuantity() + ")");
            text2.setText(String.format(Locale.getDefault(), "₹%.2f", item.getTotalPrice()));
            itemsContainer.addView(itemView);
        }

        btnPlaceOrder.setOnClickListener(v -> {
            String address = session.getAddress();
            String email = session.getEmail();
            
            int selectedId = rgSlot.getCheckedRadioButtonId();
            String slot = "Morning";
            if (selectedId == R.id.rbAfternoon) slot = "Afternoon";
            else if (selectedId == R.id.rbEvening) slot = "Evening";

            String instructions = etInstructions.getText().toString().trim();
            
            cartManager.placeOrder(address, email, slot, instructions, total);
            RewardManager.getInstance(CheckoutActivity.this).markRewardAsUsed();
            showSuccessDialog(slot, instructions);
        });
    }

    private void showSuccessDialog(String slot, String instructions) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Order Placed!");
        String message = "Your order has been placed successfully for the " + slot + " slot.";
        if (!instructions.isEmpty()) {
            message += "\n\nInstructions: " + instructions;
        }
        message += "\n\nYou can track it in the 'My Orders' section.";
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialog, which) -> {
            finish();
        });
        builder.setCancelable(false);
        builder.show();
    }
}
