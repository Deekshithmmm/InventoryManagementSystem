package main.java.com.inventory.ai.model;

import main.java.com.inventory.ai.adapter.DataAdapter;

public class ForecastService {

    private final DataAdapter data;

    public ForecastService(DataAdapter dataAdapter) {
        this.data = dataAdapter;
    }

    public double predictNextWeekSales(int productId) {

        String id = String.valueOf(productId);

        int stock = data.getCurrentStock(id);
        int expected = data.getExpectedStock(id);

        return (expected - stock) * 1.2;  // simple example
    }
}
