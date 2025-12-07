package main.java.com.inventory.ai.adapter;

import main.java.com.inventory.ai.model.SalesRecord;
import java.time.LocalDate;
import java.util.*;

public class MyDataAdapter implements DataAdapter {

    private final Map<String, Object> data = new HashMap<>();
    private final List<SalesRecord> salesHistory = new ArrayList<>();

    public MyDataAdapter() {

        data.put("price", 80.0);
        data.put("stock", 120);
        data.put("expectedStock", 150);
        data.put("freshness", 15);
        data.put("expiryDays", 40);
        data.put("demand", 70);

        salesHistory.add(new SalesRecord("101", LocalDate.now().minusDays(1), 20));
        salesHistory.add(new SalesRecord("101", LocalDate.now().minusDays(2), 18));
        salesHistory.add(new SalesRecord("101", LocalDate.now().minusDays(3), 22));
    }

    @Override
    public double getBasePrice(int productId) {
        return (double) data.get("price");
    }

    @Override
    public int getCurrentStock(String productId) {
        return (int) data.get("stock");
    }

    @Override
    public int getExpectedStock(String productId) {
        return (int) data.get("expectedStock");
    }

    @Override
    public int getFreshness(String productName) {
        return (int) data.get("freshness");
    }

    @Override
    public int getExpiryDays(String productName) {
        return (int) data.get("expiryDays");
    }

    @Override
    public int getDemandLevel(int productId) {
        return (int) data.get("demand");
    }

    @Override
    public List<SalesRecord> getSalesHistory(String productId, LocalDate sinceDate) {
        List<SalesRecord> list = new ArrayList<>();
        for (SalesRecord r : salesHistory) {
            if (r.getProductId().equals(productId) && !r.getDate().isBefore(sinceDate)) {
                list.add(r);
            }
        }
        return list;
    }

    @Override
    public int extractProductId(String msg) {
        return 101;
    }

    @Override
    public String extractProductName(String msg) {
        return "Sample Product";
    }
}

