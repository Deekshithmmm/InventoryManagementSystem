package main.java.com.inventory.ai;

import main.java.com.inventory.ai.adapter.DataAdapter;
import main.java.com.inventory.ai.model.SalesRecord;

import java.time.LocalDate;
import java.util.List;

public class InventoryChatBot {

    private final DataAdapter adapter;
    private final PredictionService prediction;
    private final ExpiryPredictionService expiry;

    public InventoryChatBot(DataAdapter adapter) {
        this.adapter = adapter;
        this.prediction = new PredictionService(adapter);
        this.expiry = new ExpiryPredictionService(adapter);
    }

    public String reply(String msg) {
        msg = msg.toLowerCase();

        if (msg.contains("low stock"))
            return getLowStock();

        if (msg.contains("expiring"))
            return getExpiring();

        if (msg.contains("sales today"))
            return getSalesToday();

        return "Try asking: low stock / expiring / sales today.";
    }

    private String getLowStock() {
        StringBuilder sb = new StringBuilder();

        for (String id : adapter.listAllProductIds()) {
            prediction.predictDaysLeft(id, 30).ifPresent(daysLeft -> {

                // FIX: Convert Double to int safely
                int days = (int) Math.round(daysLeft);

                if (days < 7) {
                    sb.append(id)
                            .append(" : ")
                            .append(days)
                            .append(" days left\n");
                }
            });
        }

        return sb.length() == 0 ? "No low-stock items." : sb.toString();
    }

    private String getExpiring() {
        List<String> exp = expiry.getExpiringProducts(30);
        if (exp.isEmpty())
            return "No expiring items in next 30 days.";

        return "Expiring soon:\n" + String.join("\n", exp);
    }

    private String getSalesToday() {
        int total = 0;

        for (String id : adapter.listAllProductIds()) {
            List<SalesRecord> records = adapter.getSalesHistory(id, LocalDate.now());
            for (SalesRecord r : records) {
                total += r.getQuantity();
            }
        }

        return "Today's total sales: " + total;
    }
}
