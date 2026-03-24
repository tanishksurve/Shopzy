package com.shopzy.fragments;

import android.content.Context;
import android.content.Intent;
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
import com.shopzy.activities.LoginActivity;
import com.shopzy.activities.OrdersActivity;
import com.shopzy.utils.CartManager;
import com.shopzy.utils.WishlistManager;

public class ProfileFragment extends Fragment {

    private TextView profileName, profileEmail, tvSavedAddress;
    private android.widget.ImageView profileImage;
    private com.shopzy.utils.SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        
        try {
            sessionManager = com.shopzy.utils.SessionManager.getInstance(requireContext());
            
            profileImage = view.findViewById(R.id.profileImage);
            profileName = view.findViewById(R.id.profileName);
            profileEmail = view.findViewById(R.id.profileEmail);
            tvSavedAddress = view.findViewById(R.id.tvSavedAddress);

            setupClickListeners(view);
            
        } catch (Throwable t) {
            t.printStackTrace();
            android.util.Log.e("ProfileFragment", "Critical error inside onCreateView", t);
            Toast.makeText(requireContext(), "Error loading profile: " + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        return view;
    }

    private void setupClickListeners(View view) {
        if (view == null) return;

        View btnBack = view.findViewById(R.id.btnBackProfile);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                if (getActivity() instanceof com.shopzy.activities.MainActivity) {
                    ((com.shopzy.activities.MainActivity) getActivity()).setSelectedTab(R.id.nav_home);
                }
            });
        }

        View cardOrders = view.findViewById(R.id.cardOrders);
        if (cardOrders != null) {
            cardOrders.setOnClickListener(v -> startActivity(new Intent(getActivity(), OrdersActivity.class)));
        }

        View cardWishlist = view.findViewById(R.id.cardWishlist);
        if (cardWishlist != null) {
            cardWishlist.setOnClickListener(v -> {
                if (getActivity() instanceof com.shopzy.activities.MainActivity) {
                    ((com.shopzy.activities.MainActivity) getActivity()).setSelectedTab(R.id.nav_wishlist);
                }
            });
        }

        View btnEditProfile = view.findViewById(R.id.btnEditProfile);
        if (btnEditProfile != null) btnEditProfile.setOnClickListener(v -> startEditProfile());
        
        View menuEditProfile = view.findViewById(R.id.menuEditProfile);
        if (menuEditProfile != null) menuEditProfile.setOnClickListener(v -> startEditProfile());

        View menuMyOrders = view.findViewById(R.id.menuMyOrders);
        if (menuMyOrders != null) {
            menuMyOrders.setOnClickListener(v -> startActivity(new Intent(getActivity(), OrdersActivity.class)));
        }

        View menuAddresses = view.findViewById(R.id.menuAddresses);
        if (menuAddresses != null) {
            menuAddresses.setOnClickListener(v -> startActivity(new Intent(getActivity(), com.shopzy.activities.EditAddressActivity.class)));
        }

        View btnLogoutTop = view.findViewById(R.id.btnLogoutTop);
        if (btnLogoutTop != null) {
            btnLogoutTop.setOnClickListener(v -> {
                if (sessionManager != null) sessionManager.logout();
                Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                if (getActivity() != null) getActivity().finish();
            });
        }

        View menuSpinWin = view.findViewById(R.id.menuSpinWin);
        if (menuSpinWin != null) {
            menuSpinWin.setOnClickListener(v -> {
                SpinWinDialogFragment dialog = SpinWinDialogFragment.newInstance();
                dialog.show(getChildFragmentManager(), "SpinWinDialog");
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            loadUserData();
            loadStats();
        } catch (Throwable t) {
            t.printStackTrace();
            android.util.Log.e("ProfileFragment", "Critical error inside onResume", t);
            if (getContext() != null) {
                Toast.makeText(getContext(), "Error in profile data: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startEditProfile() {
        startActivity(new Intent(getActivity(), com.shopzy.activities.EditProfileActivity.class));
    }

    private void loadStats() {
        if (!isAdded()) return;
        View view = getView();
        Context context = getContext();
        if (view == null || context == null || sessionManager == null) return;

        TextView orderCount = view.findViewById(R.id.tvOrderCount);
        TextView wishlistCount = view.findViewById(R.id.tvWishlistCount);

        try {
            String email = sessionManager.getEmail();
            if (orderCount != null && email != null) {
                int count = CartManager.getInstance(context).getOrderHistory(email).size();
                orderCount.setText(String.valueOf(count));
            }
            if (wishlistCount != null) {
                int count = WishlistManager.getInstance(context).getWishlist().size();
                wishlistCount.setText(String.valueOf(count));
            }
        } catch (Throwable t) {
            t.printStackTrace();
            android.util.Log.e("ProfileFragment", "Error loading stats", t);
        }
    }

    private void loadUserData() {
        if (sessionManager == null || !isAdded()) return;
        
        String name = sessionManager.getName();
        String email = sessionManager.getEmail();
        
        if (profileName != null) profileName.setText(name != null && !name.isEmpty() ? name : "User");
        if (profileEmail != null) profileEmail.setText(email != null && !email.isEmpty() ? email : "Not Logged In");
        
        String imgUri = sessionManager.getProfileImageUri();
        if (profileImage != null && imgUri != null && !imgUri.isEmpty()) {
            try {
                com.bumptech.glide.Glide.with(this)
                        .load(android.net.Uri.parse(imgUri))
                        .placeholder(R.drawable.ic_profile)
                        .error(R.drawable.ic_profile)
                        .into(profileImage);
                profileImage.setImageTintList(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String savedAddress = sessionManager.getAddress();
        if (tvSavedAddress != null) {
            if (savedAddress != null && !savedAddress.isEmpty()) {
                tvSavedAddress.setText(savedAddress);
            } else {
                tvSavedAddress.setText("No address saved yet");
            }
        }
    }
}
