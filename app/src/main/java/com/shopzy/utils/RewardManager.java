package com.shopzy.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class RewardManager {
    private static final String PREF_NAME = "ShopzyRewards";
    private static final String KEY_REWARD_TYPE = "reward_type";
    private static final String KEY_REWARD_VALUE = "reward_value";
    private static final String KEY_REWARD_UNUSED = "reward_unused";
    private static final String KEY_LAST_SPIN_DATE = "last_spin_date";

    private static RewardManager instance;
    private final SharedPreferences prefs;

    private RewardManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized RewardManager getInstance(Context context) {
        if (instance == null) {
            instance = new RewardManager(context.getApplicationContext());
        }
        return instance;
    }

    public boolean canSpinToday() {
        // Temporarily removed spin limit for testing
        return true;
        /*
        long lastSpin = prefs.getLong(KEY_LAST_SPIN_DATE, 0);
        long today = System.currentTimeMillis() / (24 * 60 * 60 * 1000);
        long lastSpinDay = lastSpin / (24 * 60 * 60 * 1000);
        return today > lastSpinDay;
        */
    }

    public void saveReward(String type, double value) {
        prefs.edit()
                .putString(KEY_REWARD_TYPE, type)
                .putLong(KEY_REWARD_VALUE, Double.doubleToLongBits(value))
                .putBoolean(KEY_REWARD_UNUSED, true)
                .putLong(KEY_LAST_SPIN_DATE, System.currentTimeMillis())
                .apply();
    }

    public Reward getUnusedReward() {
        if (!prefs.getBoolean(KEY_REWARD_UNUSED, false)) {
            return null;
        }
        String type = prefs.getString(KEY_REWARD_TYPE, "");
        double value = Double.longBitsToDouble(prefs.getLong(KEY_REWARD_VALUE, 0));
        return new Reward(type, value);
    }

    public void markRewardAsUsed() {
        prefs.edit().putBoolean(KEY_REWARD_UNUSED, false).apply();
    }

    public static class Reward {
        public String type; // PERCENT, FLAT, FREE_DELIVERY
        public double value;

        public Reward(String type, double value) {
            this.type = type;
            this.value = value;
        }
        
        public String getDisplayString() {
            switch (type) {
                case "PERCENT":
                    return (int)value + "% OFF";
                case "FLAT":
                    return "₹" + (int)value + " Cashback";
                case "FREE_DELIVERY":
                    return "Free Delivery";
                default:
                    return "No Reward";
            }
        }
    }
}
