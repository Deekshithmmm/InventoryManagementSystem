package main.java.com.inventory.ai.model;

import main.java.com.inventory.ai.adapter.DataAdapter;

public class PriceOptimizer {

    private final DataAdapter data;

    public PriceOptimizer(DataAdapter dataAdapter) {
        this.data = dataAdapter;
    }

    // Existing method
    public double getOptimizedPrice(int productId, double basePrice) {
        int demand = data.getDemandLevel(productId);

        if (demand > 80) return basePrice * 1.20;
        if (demand < 30) return basePrice * 0.85;

        return basePrice;
    }

    // ==== NEW METHOD REQUIRED BY InventoryChatBot =====
    public double suggestBestPrice(int productId) {
        double base = data.getBasePrice(productId);
        return getOptimizedPrice(productId, base);
    }
}
