package com.shopzy.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shopzy.R;
import com.shopzy.adapters.OrderAdapter;
import com.shopzy.models.Order;
import com.shopzy.utils.CartManager;

import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        ImageButton btnBack = findViewById(R.id.btnBackOrders);
        RecyclerView rvOrders = findViewById(R.id.rvOrderHistory);
        TextView tvNoOrders = findViewById(R.id.tvNoOrders);

        btnBack.setOnClickListener(v -> finish());

        com.shopzy.utils.SessionManager session = com.shopzy.utils.SessionManager.getInstance(this);
        CartManager cartManager = CartManager.getInstance(this);
        List<Order> orders = cartManager.getOrderHistory(session.getEmail());

        if (orders.isEmpty()) {
            tvNoOrders.setVisibility(View.VISIBLE);
            rvOrders.setVisibility(View.GONE);
        } else {
            tvNoOrders.setVisibility(View.GONE);
            rvOrders.setVisibility(View.VISIBLE);
            
            OrderAdapter adapter = new OrderAdapter(orders);
            rvOrders.setLayoutManager(new LinearLayoutManager(this));
            rvOrders.setAdapter(adapter);
        }
    }
}
