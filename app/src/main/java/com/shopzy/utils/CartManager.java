package com.shopzy.utils;

import com.shopzy.models.CartItem;
import com.shopzy.models.Order;
import com.shopzy.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.database.Cursor;
import com.shopzy.db.DatabaseHelper;

public class CartManager {
    private static CartManager instance;
    private DatabaseHelper dbHelper;

    private CartManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public static synchronized CartManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartManager(context.getApplicationContext());
        }
        return instance;
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("CartManager must be initialized with context first");
        }
        return instance;
    }

    public void addProduct(Product product) {
        dbHelper.addToCart(product.getId(), 1);
    }

    public void updateProductQuantity(Product product, int quantity) {
        dbHelper.updateCartQuantity(product.getId(), quantity);
    }

    public void removeProduct(Product product) {
        dbHelper.removeFromCart(product.getId());
    }

    public List<CartItem> getCartItems() {
        List<CartItem> items = new ArrayList<>();
        Cursor cursor = dbHelper.getCartItems();
        List<Product> allProducts = ProductData.getAllProducts();
        
        if (cursor.moveToFirst()) {
            do {
                String productId = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_CART_PRODUCT_ID));
                int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_CART_QUANTITY));
                
                Product product = findProductById(allProducts, productId);
                if (product != null) {
                    items.add(new CartItem(product, quantity));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    private Product findProductById(List<Product> allProducts, String id) {
        for (Product p : allProducts) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    public int getCartCount() {
        int count = 0;
        for (CartItem item : getCartItems()) {
            count += item.getQuantity();
        }
        return count;
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : getCartItems()) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void placeOrder(String address, String email, String slot, String instructions) {
        placeOrder(address, email, slot, instructions, getTotalPrice() + 10.0);
    }

    public void placeOrder(String address, String email, String slot, String instructions, double totalAmount) {
        List<CartItem> items = getCartItems();
        if (items.isEmpty()) return;
        
        String orderId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        long timestamp = System.currentTimeMillis();
        
        dbHelper.addOrder(orderId, timestamp, totalAmount, address, email, slot, instructions);
        for (CartItem item : items) {
            dbHelper.addOrderItem(orderId, item.getProduct().getId(), item.getQuantity(), item.getProduct().getPrice());
        }
        clearCart();
    }

    public List<Order> getOrderHistory(String email) {
        List<Order> orders = new ArrayList<>();
        Cursor cursor = dbHelper.getOrders(email);
        List<Product> allProducts = ProductData.getAllProducts();
        
        if (cursor.moveToFirst()) {
            do {
                String orderId = getCursorString(cursor, DatabaseHelper.COL_ORDER_ID);
                long timestamp = getCursorLong(cursor, DatabaseHelper.COL_ORDER_TIMESTAMP);
                double total = getCursorDouble(cursor, DatabaseHelper.COL_ORDER_TOTAL);
                String address = getCursorString(cursor, DatabaseHelper.COL_ORDER_ADDRESS);
                String slot = getCursorString(cursor, DatabaseHelper.COL_ORDER_SLOT);
                String instructions = getCursorString(cursor, DatabaseHelper.COL_ORDER_INSTRUCTIONS);
                
                List<CartItem> items = new ArrayList<>();
                Cursor itemCursor = dbHelper.getOrderItems(orderId);
                if (itemCursor != null) {
                    if (itemCursor.moveToFirst()) {
                        do {
                            String productId = getCursorString(itemCursor, DatabaseHelper.COL_ITEM_PRODUCT_ID);
                            int qty = getCursorInt(itemCursor, DatabaseHelper.COL_ITEM_QUANTITY);
                            Product p = findProductById(allProducts, productId);
                            if (p != null) {
                                items.add(new CartItem(p, qty));
                            }
                        } while (itemCursor.moveToNext());
                    }
                    itemCursor.close();
                }
                
                orders.add(new Order(orderId, timestamp, items, total, address, slot, instructions));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orders;
    }

    public void clearCart() {
        dbHelper.clearCart();
    }

    private String getCursorString(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return index != -1 ? cursor.getString(index) : "";
    }

    private int getCursorInt(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return index != -1 ? cursor.getInt(index) : 0;
    }

    private long getCursorLong(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return index != -1 ? cursor.getLong(index) : 0L;
    }

    private double getCursorDouble(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return index != -1 ? cursor.getDouble(index) : 0.0;
    }
}
