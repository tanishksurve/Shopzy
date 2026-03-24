package com.shopzy.adapters;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shopzy.R;
import com.shopzy.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    public static final int VIEW_TYPE_HOME = 0;
    public static final int VIEW_TYPE_GRID = 1;

    private List<Category> categories;
    private OnCategoryClickListener listener;
    private int viewType;

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }

    public CategoryAdapter(List<Category> categories, int viewType, OnCategoryClickListener listener) {
        this.categories = categories;
        this.viewType = viewType;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = (this.viewType == VIEW_TYPE_GRID) ? R.layout.item_category_grid : R.layout.item_category_home;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryName.setText(category.getName());
        holder.categoryIcon.setImageResource(category.getIconResId());
        
        if (category.getBackgroundColor() != null) {
            holder.categoryIconBg.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(category.getBackgroundColor())));
        }
        if (category.getIconColor() != null) {
            holder.categoryIcon.setImageTintList(ColorStateList.valueOf(Color.parseColor(category.getIconColor())));
        }
        
        holder.itemView.setOnClickListener(v -> listener.onCategoryClick(category));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryIcon;
        View categoryIconBg;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryIcon = itemView.findViewById(R.id.categoryIcon);
            categoryIconBg = itemView.findViewById(R.id.categoryIconBg);
        }
    }
}
