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
import com.shopzy.R;
import com.shopzy.models.Product;
import com.shopzy.utils.WishlistManager;

import java.util.List;
import java.util.Locale;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {

    private List<Product> wishlistProducts;
    private OnWishlistChangeListener changeListener;
    private OnProductClickListener clickListener;

    public interface OnWishlistChangeListener {
        void onWishlistChanged();
    }

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public WishlistAdapter(List<Product> wishlistProducts, OnWishlistChangeListener changeListener, OnProductClickListener clickListener) {
        this.wishlistProducts = wishlistProducts;
        this.changeListener = changeListener;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wishlist_product, parent, false);
        return new WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistViewHolder holder, int position) {
        Product product = wishlistProducts.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.format(Locale.getDefault(), "₹%.2f", product.getPrice()));

        Glide.with(holder.itemView.getContext())
                .load(product.getImageResId())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.productImage);

        holder.btnRemove.setOnClickListener(v -> {
            WishlistManager.getInstance(holder.itemView.getContext()).removeProduct(product);
            wishlistProducts.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, wishlistProducts.size());
            if (changeListener != null) changeListener.onWishlistChanged();
        });

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) clickListener.onProductClick(product);
        });
    }

    @Override
    public int getItemCount() {
        return wishlistProducts.size();
    }

    static class WishlistViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice;
        ImageButton btnRemove;

        public WishlistViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.wishlistProductImage);
            productName = itemView.findViewById(R.id.wishlistProductName);
            productPrice = itemView.findViewById(R.id.wishlistProductPrice);
            btnRemove = itemView.findViewById(R.id.btnRemoveWishlist);
        }
    }
}
