package com.shopzy.models;

public class Category {
    private String id;
    private String name;
    private int iconResId;
    private String backgroundColor;
    private String iconColor;

    public Category(String id, String name, int iconResId, String backgroundColor, String iconColor) {
        this.id = id;
        this.name = name;
        this.iconResId = iconResId;
        this.backgroundColor = backgroundColor;
        this.iconColor = iconColor;
    }

    public Category(String id, String name, int iconResId) {
        this(id, name, iconResId, "#F5F5F5", "#757575");
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getIconResId() { return iconResId; }
    public String getBackgroundColor() { return backgroundColor; }
    public String getIconColor() { return iconColor; }
}
