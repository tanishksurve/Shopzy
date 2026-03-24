package com.shopzy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Shopzy.db";
    private static final int DATABASE_VERSION = 3;

    // Users table
    public static final String TABLE_USERS = "users";
    public static final String COL_USER_ID = "id";
    public static final String COL_USER_EMAIL = "email";
    public static final String COL_USER_PASSWORD = "password";
    public static final String COL_USER_NAME = "name";

    // Cart table
    public static final String TABLE_CART = "cart";
    public static final String COL_CART_ID = "id";
    public static final String COL_CART_PRODUCT_ID = "productId";
    public static final String COL_CART_QUANTITY = "quantity";

    // Wishlist table
    public static final String TABLE_WISHLIST = "wishlist";
    public static final String COL_WISHLIST_ID = "id";
    public static final String COL_WISHLIST_PRODUCT_ID = "productId";

    // Orders table
    public static final String TABLE_ORDERS = "orders";
    public static final String COL_ORDER_DB_ID = "id";
    public static final String COL_ORDER_ID = "orderId";
    public static final String COL_ORDER_TIMESTAMP = "timestamp";
    public static final String COL_ORDER_TOTAL = "totalPrice";
    public static final String COL_ORDER_ADDRESS = "address";
    public static final String COL_ORDER_EMAIL = "email";
    public static final String COL_ORDER_SLOT = "deliverySlot";
    public static final String COL_ORDER_INSTRUCTIONS = "instructions";

    // Order items table
    public static final String TABLE_ORDER_ITEMS = "order_items";
    public static final String COL_ITEM_ID = "id";
    public static final String COL_ITEM_ORDER_ID = "orderId";
    public static final String COL_ITEM_PRODUCT_ID = "productId";
    public static final String COL_ITEM_QUANTITY = "quantity";
    public static final String COL_ITEM_PRICE = "price";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsers = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_NAME + " TEXT, " +
                COL_USER_EMAIL + " TEXT UNIQUE, " +
                COL_USER_PASSWORD + " TEXT)";
        
        String createCart = "CREATE TABLE " + TABLE_CART + " (" +
                COL_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CART_PRODUCT_ID + " TEXT, " +
                COL_CART_QUANTITY + " INTEGER)";

        String createWishlist = "CREATE TABLE " + TABLE_WISHLIST + " (" +
                COL_WISHLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_WISHLIST_PRODUCT_ID + " TEXT UNIQUE)";

        String createOrders = "CREATE TABLE " + TABLE_ORDERS + " (" +
                COL_ORDER_DB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ORDER_ID + " TEXT UNIQUE, " +
                COL_ORDER_TIMESTAMP + " LONG, " +
                COL_ORDER_TOTAL + " REAL, " +
                COL_ORDER_ADDRESS + " TEXT, " +
                COL_ORDER_EMAIL + " TEXT, " +
                COL_ORDER_SLOT + " TEXT, " +
                COL_ORDER_INSTRUCTIONS + " TEXT)";

        String createOrderItems = "CREATE TABLE " + TABLE_ORDER_ITEMS + " (" +
                COL_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ITEM_ORDER_ID + " TEXT, " +
                COL_ITEM_PRODUCT_ID + " TEXT, " +
                COL_ITEM_QUANTITY + " INTEGER, " +
                COL_ITEM_PRICE + " REAL, " +
                "FOREIGN KEY(" + COL_ITEM_ORDER_ID + ") REFERENCES " + TABLE_ORDERS + "(" + COL_ORDER_ID + "))";

        db.execSQL(createUsers);
        db.execSQL(createCart);
        db.execSQL(createWishlist);
        db.execSQL(createOrders);
        db.execSQL(createOrderItems);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WISHLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_ITEMS);
        onCreate(db);
    }

    // User methods
    public boolean addUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, name);
        values.put(COL_USER_EMAIL, email);
        values.put(COL_USER_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public String getUserName(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COL_USER_NAME},
                COL_USER_EMAIL + "=?", new String[]{email}, null, null, null);
        String name = "";
        if (cursor.moveToFirst()) {
            name = cursor.getString(0);
        }
        cursor.close();
        return name;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COL_USER_ID},
                COL_USER_EMAIL + "=? AND " + COL_USER_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Cart methods
    public void addToCart(String productId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CART_PRODUCT_ID, productId);
        values.put(COL_CART_QUANTITY, quantity);
        
        Cursor cursor = db.query(TABLE_CART, null, COL_CART_PRODUCT_ID + "=?", new String[]{productId}, null, null, null);
        if (cursor.moveToFirst()) {
            int currentQty = cursor.getInt(cursor.getColumnIndex(COL_CART_QUANTITY));
            values.put(COL_CART_QUANTITY, currentQty + quantity);
            db.update(TABLE_CART, values, COL_CART_PRODUCT_ID + "=?", new String[]{productId});
        } else {
            db.insert(TABLE_CART, null, values);
        }
        cursor.close();
    }

    public void updateCartQuantity(String productId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CART_QUANTITY, quantity);
        db.update(TABLE_CART, values, COL_CART_PRODUCT_ID + "=?", new String[]{productId});
    }

    public void removeFromCart(String productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, COL_CART_PRODUCT_ID + "=?", new String[]{productId});
    }

    public void clearCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, null, null);
    }

    public Cursor getCartItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_CART, null, null, null, null, null, null);
    }

    // Wishlist methods
    public void toggleWishlist(String productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (isWishlisted(productId)) {
            db.delete(TABLE_WISHLIST, COL_WISHLIST_PRODUCT_ID + "=?", new String[]{productId});
        } else {
            ContentValues values = new ContentValues();
            values.put(COL_WISHLIST_PRODUCT_ID, productId);
            db.insert(TABLE_WISHLIST, null, values);
        }
    }

    public boolean isWishlisted(String productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_WISHLIST, null, COL_WISHLIST_PRODUCT_ID + "=?", new String[]{productId}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Cursor getWishlistItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_WISHLIST, null, null, null, null, null, null);
    }

    // Order methods
    public void addOrder(String orderId, long timestamp, double total, String address, String email, String slot, String instructions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ORDER_ID, orderId);
        values.put(COL_ORDER_TIMESTAMP, timestamp);
        values.put(COL_ORDER_TOTAL, total);
        values.put(COL_ORDER_ADDRESS, address);
        values.put(COL_ORDER_EMAIL, email);
        values.put(COL_ORDER_SLOT, slot);
        values.put(COL_ORDER_INSTRUCTIONS, instructions);
        db.insert(TABLE_ORDERS, null, values);
    }

    public void addOrderItem(String orderId, String productId, int quantity, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ITEM_ORDER_ID, orderId);
        values.put(COL_ITEM_PRODUCT_ID, productId);
        values.put(COL_ITEM_QUANTITY, quantity);
        values.put(COL_ITEM_PRICE, price);
        db.insert(TABLE_ORDER_ITEMS, null, values);
    }

    public Cursor getOrders(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_ORDERS, null, COL_ORDER_EMAIL + "=?", new String[]{email}, null, null, COL_ORDER_TIMESTAMP + " DESC");
    }

    public Cursor getOrderItems(String orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_ORDER_ITEMS, null, COL_ITEM_ORDER_ID + "=?", new String[]{orderId}, null, null, null);
    }
}
