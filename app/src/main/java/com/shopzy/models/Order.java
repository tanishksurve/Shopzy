package com.shopzy.models;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private String orderId;
    private long timestamp;
    private List<CartItem> items;
    private double totalPrice;
    private String address;
    private String deliverySlot;
    private String instructions;

    public Order(String orderId, long timestamp, List<CartItem> items, double totalPrice, String address, String deliverySlot, String instructions) {
        this.orderId = orderId;
        this.timestamp = timestamp;
        this.items = items;
        this.totalPrice = totalPrice;
        this.address = address;
        this.deliverySlot = deliverySlot;
        this.instructions = instructions;
    }

    public String getOrderId() { return orderId; }
    public long getTimestamp() { return timestamp; }
    public List<CartItem> getItems() { return items; }
    public double getTotalPrice() { return totalPrice; }
    public String getAddress() { return address; }
    public String getDeliverySlot() { return deliverySlot; }
    public String getInstructions() { return instructions; }
}
