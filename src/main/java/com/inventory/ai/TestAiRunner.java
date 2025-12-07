package main.java.com.inventory.ai;

import main.java.com.inventory.ai.adapter.MyDataAdapter;
import main.java.com.inventory.ai.adapter.DataAdapter;
import main.java.com.inventory.ai.model.*;

public class TestAiRunner {

    public static void main(String[] args) {

        DataAdapter adapter = new MyDataAdapter();

        ForecastService forecast = new ForecastService(adapter);
        ExpiryPredictionService expiry = new ExpiryPredictionService(adapter);
        PriceOptimizer optimizer = new PriceOptimizer(adapter);
        AnomalyDetector anomaly = new AnomalyDetector(adapter);
        InventoryChatBot bot = new InventoryChatBot(adapter);

        System.out.println("\n==============================");
        System.out.println("     AI MODULE TEST RUNNER");
        System.out.println("==============================\n");

        // -------------------------------------------------------------
        // 1. TEST FORECAST SERVICE
        // -------------------------------------------------------------
        System.out.println("1️⃣ Testing ForecastService...");

        int productId = 101;
        double nextWeekSales = forecast.predictNextWeekSales(productId);
        System.out.println("Predicted next week sales = " + nextWeekSales);

        // -------------------------------------------------------------
        // 2. TEST EXPIRY PREDICTION
        // -------------------------------------------------------------
        System.out.println("\n2️⃣ Testing ExpiryPredictionService...");

        String productName = "apple";
        int expiryDays = expiry.predictExpiry(productName);
        System.out.println(productName + " will expire in " + expiryDays + " days");

        // -------------------------------------------------------------
        // 3. TEST PRICE OPTIMIZER
        // -------------------------------------------------------------
        System.out.println("\n3️⃣ Testing PriceOptimizer...");

        double basePrice = adapter.getBasePrice(productId);
        double optimizedPrice = optimizer.getOptimizedPrice(productId, basePrice);
        System.out.println("Optimized price = " + optimizedPrice);

        // -------------------------------------------------------------
        // 4. TEST ANOMALY DETECTOR
        // -------------------------------------------------------------
        System.out.println("\n4️⃣ Testing AnomalyDetector...");

        boolean flag = anomaly.detectStockAnomaly(productId);
        System.out.println("Stock anomaly detected? " + flag);

        // -------------------------------------------------------------
        // 5. TEST CHATBOT
        // -------------------------------------------------------------
        System.out.println("\n5️⃣ Testing InventoryChatBot...");

        System.out.println(bot.reply("forecast sales for product 101"));
        System.out.println(bot.reply("expiry of apple"));
        System.out.println(bot.reply("best price for product 101"));
        System.out.println(bot.reply("any anomaly in product 101"));
    }
}
