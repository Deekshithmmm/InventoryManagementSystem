package main.java.com.inventory.ai;

import main.java.com.inventory.ai.adapter.DataAdapter;
import main.java.com.inventory.ai.model.ForecastService;
import main.java.com.inventory.ai.model.ExpiryPredictionService;
import main.java.com.inventory.ai.model.AnomalyDetector;
import main.java.com.inventory.ai.model.PriceOptimizer;
import main.java.com.inventory.ai.model.PredictionService;
import main.java.com.inventory.ai.model.SalesRecord;

public class InventoryChatBot {

    private final DataAdapter data;
    private final ForecastService forecast;
    private final ExpiryPredictionService expiry;
    private final AnomalyDetector anomaly;
    private final PriceOptimizer optimizer;
    private final PredictionService prediction;

    public InventoryChatBot(DataAdapter dataAdapter) {
        this.data = dataAdapter;

        this.forecast = new ForecastService(dataAdapter);
        this.expiry = new ExpiryPredictionService(dataAdapter);
        this.anomaly = new AnomalyDetector(dataAdapter);
        this.optimizer = new PriceOptimizer(dataAdapter);
        this.prediction = new PredictionService(dataAdapter);
    }

    public String reply(String msg) {
        msg = msg.toLowerCase();

        // -----------------------------
        // 1. Forecast next week
        // -----------------------------
        if (msg.contains("forecast") || msg.contains("next week")) {
            int id = extractProductId(msg);
            double result = forecast.predictNextWeekSales(id);
            return "📊 Forecast next-week sales for product " + id + " = " + result + " units";
        }

        // -----------------------------
        // 2. Expiry prediction
        // -----------------------------
        if (msg.contains("expiry") || msg.contains("expire")) {
            String name = extractProductName(msg);
            int days = expiry.predictExpiry(name);
            return "⏳ Product '" + name + "' will expire in approx " + days + " days.";
        }

        // -----------------------------
        // 3. Anomaly detection
        // -----------------------------
        if (msg.contains("anomaly") || msg.contains("issue") || msg.contains("problem")) {
            int id = extractProductId(msg);
            boolean abnormal = anomaly.detectStockAnomaly(id);
            return abnormal
                    ? "⚠ Stock anomaly detected for product " + id
                    : "✔ Stock level normal for product " + id;
        }

        // -----------------------------
        // 4. Price optimization
        // -----------------------------
        if (msg.contains("price") || msg.contains("best price")) {
            int id = extractProductId(msg);
            double best = optimizer.suggestBestPrice(id);
            return "💰 Suggested optimized price for product " + id + " = ₹" + best;
        }

        // -----------------------------
        // 5. AVG Daily Usage (PredictionService)
        // -----------------------------
        if (msg.contains("avg usage") || msg.contains("daily usage")) {
            int id = extractProductId(msg);
            var avg = prediction.computeAvgDailyUsage(id + "", 30);

            return avg.isPresent()
                    ? "📈 Average daily usage for product " + id + " ≈ " + avg.getAsDouble()
                    : "No usage data found for product " + id;
        }

        // -----------------------------
        // 6. Predict how long stock will last
        // -----------------------------
        if (msg.contains("days left") || msg.contains("how long") || msg.contains("stock last")) {
            int id = extractProductId(msg);
            var result = prediction.predictDaysLeft(id + "", 30);

            return result.isPresent()
                    ? "⏳ Product " + id + " will last approx " + String.format("%.1f", result.get()) + " days."
                    : "Cannot predict remaining days for product " + id;
        }

        // -----------------------------
        // 7. Show sales history
        // -----------------------------
        if (msg.contains("sales history") || msg.contains("show sales")) {
            int id = extractProductId(msg);
            var list = data.getSalesHistory(id + "", java.time.LocalDate.now().minusDays(30));


            if (list.isEmpty())
                return "No sales history for product " + id;

            StringBuilder sb = new StringBuilder("📘 Sales History for product " + id + ":\n");
            list.forEach(s -> sb.append("• ").append(s).append("\n"));

            return sb.toString();
        }

        return "❓ I didn't understand. Try:\n" +
                "➡ forecast 101\n" +
                "➡ anomaly 101\n" +
                "➡ best price 101\n" +
                "➡ avg usage 101\n" +
                "➡ days left 101\n" +
                "➡ sales history 101\n";
    }

    // ---------------------------
    // Utility extractors
    // ---------------------------
    private int extractProductId(String msg) {
        return msg.replaceAll("[^0-9]", "").isEmpty()
                ? 0
                : Integer.parseInt(msg.replaceAll("[^0-9]", ""));
    }

    private String extractProductName(String msg) {
        return msg.replace("expiry", "")
                .replace("expire", "")
                .trim();
    }
}
