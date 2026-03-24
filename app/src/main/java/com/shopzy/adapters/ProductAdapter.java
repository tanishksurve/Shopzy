package com.shopzy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.shopzy.R;
import com.shopzy.models.Product;
import com.shopzy.utils.WishlistManager;

import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;
    private OnProductClickListener listener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
        void onAddToCartClick(Product product);
    }

    public ProductAdapter(List<Product> products, OnProductClickListener listener) {
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.format(Locale.getDefault(), "₹%.2f", product.getPrice()));

        // Load product image
        Glide.with(holder.itemView.getContext())
                .load(product.getImageResId())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.productImage);

        // Set wishlist heart icon state
        updateWishlistIcon(holder, product);

        // Product card click
        holder.itemView.setOnClickListener(v -> listener.onProductClick(product));

        // Add to cart
        holder.btnAddToCart.setOnClickListener(v -> listener.onAddToCartClick(product));

        // Wishlist toggle
        holder.btnWishlist.setOnClickListener(v -> {
            WishlistManager wm = WishlistManager.getInstance(holder.itemView.getContext());
            if (wm.isWishlisted(product)) {
                wm.removeProduct(product);
            } else {
                wm.addProduct(product);
            }
            updateWishlistIcon(holder, product);
        });
    }

    private void updateWishlistIcon(ProductViewHolder holder, Product product) {
        if (WishlistManager.getInstance(holder.itemView.getContext()).isWishlisted(product)) {
            holder.btnWishlist.setImageResource(R.drawable.ic_heart_filled);
        } else {
            holder.btnWishlist.setImageResource(R.drawable.ic_heart_outline);
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice;
        MaterialCardView btnAddToCart;
        ImageButton btnWishlist;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
            btnWishlist = itemView.findViewById(R.id.btnWishlist);
        }
    }
}
