package main.java.com.inventory.ai.model;

import main.java.com.inventory.ai.adapter.DataAdapter;

public class ExpiryPredictionService {

    private final DataAdapter data;

    public ExpiryPredictionService(DataAdapter data) {
        this.data = data;
    }

    public int predictExpiry(String productName) {
        int freshness = data.getFreshness(productName);
        return Math.max(1, 30 - freshness);
    }
}
