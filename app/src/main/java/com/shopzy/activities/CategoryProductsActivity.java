package com.shopzy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shopzy.R;
import com.shopzy.adapters.ProductAdapter;
import com.shopzy.models.Product;
import com.shopzy.utils.CartManager;
import com.shopzy.utils.ProductData;

import java.util.List;

public class CategoryProductsActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {

    private RecyclerView rvProducts;
    private ProductAdapter adapter;
    private TextView tvCategoryName;
    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);

        categoryName = getIntent().getStringExtra("category_name");
        if (categoryName == null) categoryName = "Products";

        tvCategoryName = findViewById(R.id.tvCategoryName);
        rvProducts = findViewById(R.id.rvCategoryProducts);
        ImageButton btnBack = findViewById(R.id.btnBack);

        tvCategoryName.setText(categoryName);

        // Setup Back Button - always navigate to Home
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        List<Product> products = ProductData.getProductsByCategory(categoryName);
        adapter = new ProductAdapter(products, this);
        rvProducts.setLayoutManager(new GridLayoutManager(this, 2));
        rvProducts.setAdapter(adapter);
    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }

    @Override
    public void onAddToCartClick(Product product) {
        CartManager.getInstance(CategoryProductsActivity.this).addProduct(product);
        Toast.makeText(this, product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
    }
}
