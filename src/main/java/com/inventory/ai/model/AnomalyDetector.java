package main.java.com.inventory.ai.model;

import main.java.com.inventory.ai.adapter.DataAdapter;

public class AnomalyDetector {

    private final DataAdapter data;

    public AnomalyDetector(DataAdapter dataAdapter) {
        this.data = dataAdapter;
    }

    // === NEW METHOD REQUIRED BY InventoryChatBot ===
    public boolean detectStockAnomaly(int productId) {

        String id = String.valueOf(productId);

        int actual = data.getCurrentStock(id);
        int expected = data.getExpectedStock(id);

        return isAnomaly(actual, expected);
    }

    private boolean isAnomaly(int actual, int expected) {
        return Math.abs(actual - expected) > (expected * 0.20);
    }
}
