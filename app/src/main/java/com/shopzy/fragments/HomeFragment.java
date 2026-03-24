package com.shopzy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Handler;
import android.os.Looper;

import com.shopzy.R;
import com.shopzy.activities.CartActivity;
import com.shopzy.activities.ProductDetailActivity;
import com.shopzy.adapters.CategoryAdapter;
import com.shopzy.adapters.ProductAdapter;
import com.shopzy.models.Category;
import com.shopzy.models.Product;
import com.shopzy.utils.CartManager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ProductAdapter.OnProductClickListener, CategoryAdapter.OnCategoryClickListener {

    private RecyclerView rvCategories, rvProducts;
    private ProductAdapter productAdapter;
    private CategoryAdapter categoryAdapter;
    private TextView cartBadge;
    
    private ViewPager2 vpBanner;
    private final Handler sliderHandler = new Handler(Looper.getMainLooper());
    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            if (vpBanner != null && vpBanner.getAdapter() != null) {
                int currentItem = vpBanner.getCurrentItem();
                int nextItem = (currentItem + 1) % vpBanner.getAdapter().getItemCount();
                vpBanner.setCurrentItem(nextItem, true);
                sliderHandler.postDelayed(sliderRunnable, 5000);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvCategories = view.findViewById(R.id.rvCategories);
        rvProducts = view.findViewById(R.id.rvProducts);
        ImageButton btnCart = view.findViewById(R.id.btnCart);
        cartBadge = view.findViewById(R.id.cartBadge);
        vpBanner = view.findViewById(R.id.vpBanner);

        btnCart.setOnClickListener(v -> startActivity(new Intent(getActivity(), CartActivity.class)));

        setupBanner();
        setupCategories();
        setupProducts();
        refreshCartBadge();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshCartBadge();
        sliderHandler.postDelayed(sliderRunnable, 5000);
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    private void setupBanner() {
        List<Integer> bannerImages = new ArrayList<>();
        bannerImages.add(R.drawable.bannerimg1);
        bannerImages.add(R.drawable.bannerimg2);
        bannerImages.add(R.drawable.bannerimg3);
        bannerImages.add(R.drawable.bannerimg4);
        bannerImages.add(R.drawable.bannerimg5);

        com.shopzy.adapters.BannerAdapter bannerAdapter = new com.shopzy.adapters.BannerAdapter(bannerImages);
        vpBanner.setAdapter(bannerAdapter);
        vpBanner.setClipToPadding(false);
        vpBanner.setClipChildren(false);
        vpBanner.setOffscreenPageLimit(3);
        vpBanner.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        vpBanner.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 5000);
            }
        });
    }

    private void refreshCartBadge() {
        if (cartBadge == null) return;
        int count = CartManager.getInstance(getContext()).getCartCount();
        if (count > 0) {
            cartBadge.setVisibility(View.VISIBLE);
            cartBadge.setText(count > 99 ? "99+" : String.valueOf(count));
        } else {
            cartBadge.setVisibility(View.GONE);
        }
    }

    private void setupCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("1", "Mobiles", R.drawable.mobileicon, "#E3F2FD", "#2196F3"));
        categories.add(new Category("2", "Electronics", R.drawable.electronics, "#FFF3E0", "#FF9800"));
        categories.add(new Category("3", "Fashion", R.drawable.fashion, "#F3E5F5", "#9C27B0"));
        categories.add(new Category("4", "Grocery", R.drawable.grocery, "#E8F5E9", "#4CAF50"));
        categories.add(new Category("5", "Home & Furniture", R.drawable.home_furniture, "#FFF8E1", "#FFC107"));
        categories.add(new Category("6", "Footwear", R.drawable.footwear, "#FCE4EC", "#E91E63")); // Fallback to fashion
        categories.add(new Category("7", "Beauty", R.drawable.beauty, "#FDF2F8", "#EC407A"));
        categories.add(new Category("8", "Toys & Kids", R.drawable.toys_kids, "#FFFDE7", "#FBC02D"));

        categoryAdapter = new CategoryAdapter(categories, CategoryAdapter.VIEW_TYPE_HOME, this);
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvCategories.setAdapter(categoryAdapter);
    }

    private void setupProducts() {
        List<Product> allProducts = com.shopzy.utils.ProductData.getAllProducts();
        List<Product> trendingProducts = new ArrayList<>();
        List<String> seenCategories = new ArrayList<>();

        for (Product product : allProducts) {
            if (!seenCategories.contains(product.getCategory())) {
                trendingProducts.add(product);
                seenCategories.add(product.getCategory());
            }
        }

        productAdapter = new ProductAdapter(trendingProducts, this);
        rvProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvProducts.setAdapter(productAdapter);
    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }

    @Override
    public void onAddToCartClick(Product product) {
        CartManager.getInstance(getContext()).addProduct(product);
        Toast.makeText(getContext(), product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
        refreshCartBadge();
    }

    @Override
    public void onCategoryClick(Category category) {
        Intent intent = new Intent(getActivity(), com.shopzy.activities.CategoryProductsActivity.class);
        intent.putExtra("category_name", category.getName());
        startActivity(intent);
    }
}
