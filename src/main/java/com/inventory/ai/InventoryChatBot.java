package main.java.com.inventory.ai;

import main.java.com.inventory.ai.adapter.DataAdapter;
import main.java.com.inventory.ai.model.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;       // ✅ REQUIRED
import java.util.OptionalDouble;

import java.time.LocalDate;
import java.util.List;
import java.util.OptionalDouble;

public class InventoryChatBot {

    private final DataAdapter data;
    private final PredictionService prediction;
    private final ForecastService forecast;
    private final ExpiryPredictionService expiry;
    private final AnomalyDetector anomaly;

    public InventoryChatBot(DataAdapter data) {
        this.data = data;
        this.prediction = new PredictionService(data);
        this.forecast = new ForecastService(data);
        this.expiry = new ExpiryPredictionService(data);
        this.anomaly = new AnomalyDetector(data);
    }

    public String reply(String msg) {

        msg = msg.toLowerCase();

        // === HELP COMMAND ==================================================
        if (msg.contains("help")) {
            return """
                    🤖 Available Commands:
                    --------------------------------
                    • sales history for <id>
                    • predict expiry for <id>
                    • predict days left for <id>
                    • forecast next week for <id>
                    • price for <id>
                    • demand level for <id>
                    • detect anomaly for <id>
                    --------------------------------
                    Example: "Show sales history for 101"
                    """;
        }

        // === SALES HISTORY ==================================================
        if (msg.contains("sales history")) {
            int id = data.extractProductId(msg);
            LocalDate since = LocalDate.now().minusDays(30);
            List<SalesRecord> list = data.getSalesHistory(String.valueOf(id), since);

            if (list.isEmpty()) {
                return "No sales history found for product " + id;
            }

            StringBuilder sb = new StringBuilder("📊 Sales history for product " + id + ":\n\n");
            list.forEach(s -> sb.append("- ").append(s).append("\n"));
            return sb.toString();
        }

        // === EXPIRY PREDICTION =============================================
        if (msg.contains("predict expiry")) {
            String name = data.extractProductName(msg);
            int days = expiry.predictExpiry(name);
            return "🧪 Expected expiry for " + name + " is " + days + " days.";
        }

        // === PREDICT DAYS LEFT =============================================
        if (msg.contains("predict days left")) {
            int id = data.extractProductId(msg);

            Optional<Double> daysLeft = prediction.predictDaysLeft(String.valueOf(id), 30);


            if (daysLeft.isEmpty()) {
                return "Not enough data to calculate days left for product " + id;
            }

            return "📅 Estimated " + daysLeft.get() + " days left before stock runs out.";
        }

        // === FORECAST NEXT WEEK ============================================
        if (msg.contains("forecast next week")) {
            int id = data.extractProductId(msg);
            double result = forecast.predictNextWeekSales(id);
            return "📈 Forecast next-week sales for product " + id + " = " + result + " units.";
        }

        // === PRICE LOOKUP ===================================================
        if (msg.contains("price")) {
            int id = data.extractProductId(msg);
            double price = data.getBasePrice(id);
            return "💰 Base price for product " + id + " = ₹" + price;
        }

        // === DEMAND LEVEL ===================================================
        if (msg.contains("demand")) {
            int id = data.extractProductId(msg);
            int demand = data.getDemandLevel(id);
            return "🔥 Demand level for product " + id + " = " + demand + "/100";
        }

        // === ANOMALY DETECTION =============================================
        if (msg.contains("anomaly") || msg.contains("detect")) {
            int id = data.extractProductId(msg);
            boolean isAbnormal = anomaly.detectStockAnomaly(id);

            if (isAbnormal) {
                return "⚠️ ANOMALY DETECTED for product " + id +
                        "! Stock or freshness looks abnormal.";
            } else {
                return "✅ No anomaly detected for product " + id;
            }
        }

        // UNKNOWN COMMAND
        return "❓ I didn’t understand that. Type **help** to see available commands.";
    }
}
