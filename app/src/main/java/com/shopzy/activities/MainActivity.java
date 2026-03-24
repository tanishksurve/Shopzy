package com.shopzy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shopzy.R;
import com.shopzy.adapters.MainViewPagerAdapter;
import com.shopzy.fragments.CategoriesFragment;
import com.shopzy.fragments.HomeFragment;
import com.shopzy.fragments.ProfileFragment;
import com.shopzy.fragments.WishlistFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 viewPager = findViewById(R.id.view_pager);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Sync ViewPager2 with BottomNavigationView
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNav.setSelectedItemId(R.id.nav_home);
                        break;
                    case 1:
                        bottomNav.setSelectedItemId(R.id.nav_categories);
                        break;
                    case 2:
                        bottomNav.setSelectedItemId(R.id.nav_wishlist);
                        break;
                    case 3:
                        bottomNav.setSelectedItemId(R.id.nav_profile);
                        break;
                }
            }
        });

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                viewPager.setCurrentItem(0);
                return true;
            } else if (itemId == R.id.nav_categories) {
                viewPager.setCurrentItem(1);
                return true;
            } else if (itemId == R.id.nav_wishlist) {
                viewPager.setCurrentItem(2);
                return true;
            } else if (itemId == R.id.nav_profile) {
                viewPager.setCurrentItem(3);
                return true;
            }
            return false;
        });

        ImageButton btnCart = findViewById(R.id.btnCart);
        if (btnCart != null) {
            btnCart.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            });
        }
    }

    public void setSelectedTab(int itemId) {
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        if (viewPager != null) {
            if (itemId == R.id.nav_home) viewPager.setCurrentItem(0);
            else if (itemId == R.id.nav_categories) viewPager.setCurrentItem(1);
            else if (itemId == R.id.nav_wishlist) viewPager.setCurrentItem(2);
            else if (itemId == R.id.nav_profile) viewPager.setCurrentItem(3);
        }
    }
}
