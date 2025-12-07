package main.java.com.inventory.ai.adapter;

import main.java.com.inventory.ai.model.SalesRecord;
import java.time.LocalDate;
import java.util.List;

public interface DataAdapter {

    double getBasePrice(int productId);

    int getCurrentStock(String productId);

    int getExpectedStock(String productId);

    int getFreshness(String productName);

    int getExpiryDays(String productName);

    int getDemandLevel(int productId);

    List<SalesRecord> getSalesHistory(String productId, LocalDate sinceDate);

    int extractProductId(String message);

    String extractProductName(String message);
}
