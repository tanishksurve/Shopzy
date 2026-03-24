package com.shopzy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.shopzy.R;
import com.shopzy.models.Product;
import com.shopzy.utils.CartManager;
import com.shopzy.utils.WishlistManager;

import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        product = (Product) getIntent().getSerializableExtra("product");

        ImageView image = findViewById(R.id.detailProductImage);
        TextView name = findViewById(R.id.detailProductName);
        TextView price = findViewById(R.id.detailProductPrice);
        TextView desc = findViewById(R.id.detailProductDesc);
        RatingBar rating = findViewById(R.id.detailProductRating);
        ImageButton btnBack = findViewById(R.id.btnBack);
        Button btnAdd = findViewById(R.id.btnAddToCart);
        Button btnWishlist = findViewById(R.id.btnAddToWishlist);

        if (product != null) {
            name.setText(product.getName());
            price.setText(String.format(Locale.getDefault(), "₹%.2f", product.getPrice()));
            desc.setText(product.getDescription());
            rating.setRating(product.getRating());
            Glide.with(this).load(product.getImageResId()).into(image);
            
            if (WishlistManager.getInstance(this).isWishlisted(product)) {
                btnWishlist.setText("In Wishlist");
                btnWishlist.setEnabled(false);
            }
        }

        btnBack.setOnClickListener(v -> finish());
        
        btnAdd.setOnClickListener(v -> {
            CartManager.getInstance(this).addProduct(product);
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
            navigateToHome();
        });
 
        btnWishlist.setOnClickListener(v -> {
            WishlistManager.getInstance(this).addProduct(product);
            Toast.makeText(this, "Added to wishlist", Toast.LENGTH_SHORT).show();
            navigateToHome();
        });
    }
 
    private void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
