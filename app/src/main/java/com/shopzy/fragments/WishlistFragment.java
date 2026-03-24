package com.shopzy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shopzy.R;
import com.shopzy.adapters.WishlistAdapter;
import com.shopzy.models.Product;
import com.shopzy.utils.WishlistManager;

import java.util.ArrayList;
import java.util.List;

public class WishlistFragment extends Fragment {

    private RecyclerView rvWishlist;
    private LinearLayout llEmptyWishlist;
    private WishlistAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        rvWishlist = view.findViewById(R.id.rvWishlist);
        llEmptyWishlist = view.findViewById(R.id.llEmptyWishlist);

        View btnBack = view.findViewById(R.id.btnBackWishlist);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                if (getActivity() instanceof com.shopzy.activities.MainActivity) {
                    ((com.shopzy.activities.MainActivity) getActivity()).setSelectedTab(R.id.nav_home);
                }
            });
        }

        updateWishlist();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateWishlist();
    }

    private void updateWishlist() {
        WishlistManager wishlistManager = WishlistManager.getInstance(getContext());
        List<Product> wishlist = new ArrayList<>(wishlistManager.getWishlist());

        if (wishlist.isEmpty()) {
            rvWishlist.setVisibility(View.GONE);
            llEmptyWishlist.setVisibility(View.VISIBLE);
        } else {
            llEmptyWishlist.setVisibility(View.GONE);
            rvWishlist.setVisibility(View.VISIBLE);
            
            adapter = new WishlistAdapter(wishlist, this::updateWishlist, product -> {
                Intent intent = new Intent(getActivity(), com.shopzy.activities.ProductDetailActivity.class);
                intent.putExtra("product", product);
                startActivity(intent);
            });
            rvWishlist.setLayoutManager(new LinearLayoutManager(getContext()));
            rvWishlist.setAdapter(adapter);
        }
    }
}
