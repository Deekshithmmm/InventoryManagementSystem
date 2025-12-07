package main.java.com.inventory.ai;

import main.java.com.inventory.ai.adapter.DataAdapter;
import main.java.com.inventory.ai.adapter.MyDataAdapter;


public class TestAiRunner {

    public static void main(String[] args) {

        DataAdapter adapter = new MyDataAdapter();


        PredictionService prediction = new PredictionService(adapter);
        ForecastService forecast = new ForecastService(adapter);
        PriceOptimizer optimizer = new PriceOptimizer(adapter);
        AnomalyDetector anomaly = new AnomalyDetector(adapter);
        InventoryChatBot bot = new InventoryChatBot(adapter);

        System.out.println("========================================");
        System.out.println("        AI MODULE TEST RUNNER");
        System.out.println("========================================\n");

        // -------------------------------------------------------
        // 1. Test: List all products
        // -------------------------------------------------------
        System.out.println("📌 All Products:");
        adapter.listAllProductIds().forEach(id -> System.out.println(" - " + id));
        System.out.println();


        // -------------------------------------------------------
        // 2. Test Stock Prediction
        // -------------------------------------------------------
        System.out.println("📌 Stock Prediction (days left):");
        for (String id : adapter.listAllProductIds()) {
            prediction.predictDaysLeft(id, 30).ifPresent(days -> {
                int d = (int) Math.round(days);
                System.out.println(" - " + id + ": " + d + " days left");
            });
        }
        System.out.println();


        // -------------------------------------------------------
        // 3. Test Forecasting
        // -------------------------------------------------------
        System.out.println("📌 Forecast Next Month Sales:");
        for (String id : adapter.listAllProductIds()) {
            double nextMonth = forecast.forecastNextMonth(id, 30);
            System.out.println(" - " + id + ": ~" + ((int) nextMonth) + " units");
        }
        System.out.println();


        // -------------------------------------------------------
        // 4. Test Price Optimizer
        // -------------------------------------------------------
        System.out.println("📌 Price Optimizer:");
        for (String id : adapter.listAllProductIds()) {
            optimizer.recommendPrice(id, 1.2, 0.15).ifPresent(price -> {
                System.out.println(" - Recommended for " + id + ": ₹" + price);
            });
        }
        System.out.println();


        // -------------------------------------------------------
        // 5. Test Anomaly Detection
        // -------------------------------------------------------
        System.out.println("📌 Anomaly Detector:");
        for (String id : adapter.listAllProductIds()) {
            boolean suspicious = anomaly.isAnomalousRemoval(id, 20, 30, 2.5);
            System.out.println(" - " + id + ": " + (suspicious ? "⚠️ Suspicious" : "OK"));
        }
        System.out.println();


        // -------------------------------------------------------
        // 6. Test ChatBot
        // -------------------------------------------------------
        System.out.println("📌 ChatBot Tests:");

        System.out.println("User: low stock\nBot: " + bot.reply("low stock"));
        System.out.println("--------------------------------------");
        System.out.println("User: expiring\nBot: " + bot.reply("expiring"));
        System.out.println("--------------------------------------");
        System.out.println("User: sales today\nBot: " + bot.reply("sales today"));
        System.out.println("--------------------------------------");

        System.out.println("\n========================================");
        System.out.println("     AI MODULE TEST FINISHED");
        System.out.println("========================================");
    }
}

