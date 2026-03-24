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
import com.shopzy.models.CartItem;

import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private OnCartInteractionListener listener;

    public interface OnCartInteractionListener {
        void onQuantityChanged();
        void onRemoveItem();
        void onProductClick(com.shopzy.models.Product product);
    }

    public CartAdapter(List<CartItem> cartItems, OnCartInteractionListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.name.setText(item.getProduct().getName());
        holder.price.setText(String.format(Locale.getDefault(), "₹%.2f", item.getProduct().getPrice()));
        holder.qty.setText(String.valueOf(item.getQuantity()));

        Glide.with(holder.itemView.getContext())
                .load(item.getProduct().getImageResId())
                .into(holder.image);

        holder.btnAdd.setOnClickListener(v -> {
            int newQty = item.getQuantity() + 1;
            item.setQuantity(newQty);
            com.shopzy.utils.CartManager.getInstance(holder.itemView.getContext()).updateProductQuantity(item.getProduct(), newQty);
            notifyItemChanged(holder.getAdapterPosition());
            listener.onQuantityChanged();
        });
        holder.btnRemove.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                int newQty = item.getQuantity() - 1;
                item.setQuantity(newQty);
                com.shopzy.utils.CartManager.getInstance(holder.itemView.getContext()).updateProductQuantity(item.getProduct(), newQty);
                notifyItemChanged(holder.getAdapterPosition());
                listener.onQuantityChanged();
            }
        });
        holder.btnDelete.setOnClickListener(v -> {
            int currentPos = holder.getAdapterPosition();
            if (currentPos != RecyclerView.NO_POSITION) {
                com.shopzy.utils.CartManager.getInstance(holder.itemView.getContext()).removeProduct(item.getProduct());
                cartItems.remove(currentPos);
                notifyItemRemoved(currentPos);
                notifyItemRangeChanged(currentPos, cartItems.size());
                listener.onRemoveItem();
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onProductClick(item.getProduct());
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price, qty;
        ImageButton btnAdd, btnRemove, btnDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.cartItemImage);
            name = itemView.findViewById(R.id.cartItemName);
            price = itemView.findViewById(R.id.cartItemPrice);
            qty = itemView.findViewById(R.id.cartItemQty);
            btnAdd = itemView.findViewById(R.id.btnAddQty);
            btnRemove = itemView.findViewById(R.id.btnRemoveQty);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
