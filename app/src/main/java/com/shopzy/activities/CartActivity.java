package com.shopzy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shopzy.R;
import com.shopzy.adapters.CartAdapter;
import com.shopzy.utils.CartManager;

import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private TextView tvTotal, tvSubtotal, tvEmptyCart;
    private View summaryLayout, deliveryDetails;
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ImageButton btnBack = findViewById(R.id.btnBackCart);
        RecyclerView rvItems = findViewById(R.id.rvCartItems);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvTotal = findViewById(R.id.tvCartTotal);
        tvEmptyCart = findViewById(R.id.tvEmptyCart);
        summaryLayout = findViewById(R.id.summaryLayout);
        deliveryDetails = findViewById(R.id.deliveryDetails);
        
        TextView tvCartAddress = findViewById(R.id.tvCartAddress);
        TextView tvCartEmail = findViewById(R.id.tvCartEmail);
        Button btnCheckout = findViewById(R.id.btnProceedCheckout);
  
        com.shopzy.utils.SessionManager session = com.shopzy.utils.SessionManager.getInstance(this);
        String savedAddress = session.getAddress();
        tvCartAddress.setText(savedAddress.isEmpty() ? "No address saved. Please set your address in Profile." : savedAddress);
        tvCartEmail.setText(session.getEmail());
  
        btnBack.setOnClickListener(v -> finish());

        CartManager cartManager = CartManager.getInstance(this);
        
        adapter = new CartAdapter(cartManager.getCartItems(), new CartAdapter.OnCartInteractionListener() {
            @Override
            public void onQuantityChanged() {
                updateTotals();
            }

            @Override
            public void onRemoveItem() {
                updateTotals();
            }

            @Override
            public void onProductClick(com.shopzy.models.Product product) {
                Intent intent = new Intent(CartActivity.this, ProductDetailActivity.class);
                intent.putExtra("product", product);
                startActivity(intent);
            }
        });

        rvItems.setLayoutManager(new LinearLayoutManager(this));
        rvItems.setAdapter(adapter);

        updateTotals();

        btnCheckout.setOnClickListener(v -> {
            if (cartManager.getCartItems().isEmpty()) {
                Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(CartActivity.this, CheckoutActivity.class));
                finish();
            }
        });
    }

    private void updateTotals() {
        List<com.shopzy.models.CartItem> items = CartManager.getInstance(this).getCartItems();
        if (items.isEmpty()) {
            tvEmptyCart.setVisibility(View.VISIBLE);
            summaryLayout.setVisibility(View.GONE);
            deliveryDetails.setVisibility(View.GONE);
        } else {
            tvEmptyCart.setVisibility(View.GONE);
            summaryLayout.setVisibility(View.VISIBLE);
            deliveryDetails.setVisibility(View.VISIBLE);
            
            double subtotal = CartManager.getInstance(this).getTotalPrice();
            tvSubtotal.setText(String.format(Locale.getDefault(), "₹%.2f", subtotal));
            tvTotal.setText(String.format(Locale.getDefault(), "₹%.2f", subtotal + 10.0));
        }
    }
}
