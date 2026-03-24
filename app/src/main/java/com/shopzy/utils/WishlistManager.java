package com.shopzy.utils;

import com.shopzy.models.Product;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import com.shopzy.db.DatabaseHelper;

public class WishlistManager {
    private static WishlistManager instance;
    private DatabaseHelper dbHelper;

    private WishlistManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public static synchronized WishlistManager getInstance(Context context) {
        if (instance == null) {
            instance = new WishlistManager(context.getApplicationContext());
        }
        return instance;
    }

    public static synchronized WishlistManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("WishlistManager must be initialized with context first");
        }
        return instance;
    }

    public void addProduct(Product product) {
        if (!isWishlisted(product)) {
            dbHelper.toggleWishlist(product.getId());
        }
    }

    public void removeProduct(Product product) {
        if (isWishlisted(product)) {
            dbHelper.toggleWishlist(product.getId());
        }
    }

    public boolean isWishlisted(Product product) {
        return dbHelper.isWishlisted(product.getId());
    }

    public List<Product> getWishlist() {
        List<Product> list = new ArrayList<>();
        Cursor cursor = dbHelper.getWishlistItems();
        List<Product> allProducts = ProductData.getAllProducts();
        
        if (cursor.moveToFirst()) {
            do {
                String productId = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_WISHLIST_PRODUCT_ID));
                Product p = findProductById(allProducts, productId);
                if (p != null) {
                    list.add(p);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    private Product findProductById(List<Product> allProducts, String id) {
        for (Product p : allProducts) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }
}
