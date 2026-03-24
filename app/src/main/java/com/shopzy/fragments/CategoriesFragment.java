package com.shopzy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shopzy.R;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shopzy.adapters.CategoryAdapter;
import com.shopzy.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    private RecyclerView rvCategories;
    private CategoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        rvCategories = view.findViewById(R.id.rvCategories);
        rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));

        List<Category> categories = new ArrayList<>();
        categories.add(new Category("1", "Mobiles", R.drawable.mobileicon, "#E3F2FD", "#2196F3"));
        categories.add(new Category("2", "Electronics", R.drawable.electronics, "#FFF3E0", "#FF9800"));
        categories.add(new Category("3", "Fashion", R.drawable.fashion, "#F3E5F5", "#9C27B0"));
        categories.add(new Category("4", "Grocery", R.drawable.grocery, "#E8F5E9", "#4CAF50"));
        categories.add(new Category("5", "Home & Furniture", R.drawable.home_furniture, "#FFF8E1", "#FFC107"));
        categories.add(new Category("6", "Footwear", R.drawable.footwear, "#FCE4EC", "#E91E63")); // Fallback
        categories.add(new Category("7", "Beauty", R.drawable.beauty, "#FDF2F8", "#EC407A"));
        categories.add(new Category("8", "Toys & Kids", R.drawable.toys_kids, "#FFFDE7", "#FBC02D"));

        adapter = new CategoryAdapter(categories, CategoryAdapter.VIEW_TYPE_GRID, category -> {
            android.content.Intent intent = new android.content.Intent(getActivity(), com.shopzy.activities.CategoryProductsActivity.class);
            intent.putExtra("category_name", category.getName());
            startActivity(intent);
        });
        rvCategories.setAdapter(adapter);

        view.findViewById(R.id.btnBack).setOnClickListener(v -> {
            if (getActivity() != null) {
                com.google.android.material.bottomnavigation.BottomNavigationView bottomNav = 
                    getActivity().findViewById(R.id.bottom_navigation);
                if (bottomNav != null) {
                    bottomNav.setSelectedItemId(R.id.nav_home);
                }
            }
        });

        return view;
    }
}
