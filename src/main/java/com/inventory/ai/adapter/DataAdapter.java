package main.java.com.inventory.ai.adapter;

import main.java.com.inventory.ai.model.SalesRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DataAdapter {
    List<String> listAllProductIds();

    Optional<String> getProductName(String productId);

    Optional<Integer> getCurrentStock(String productId);

    List<SalesRecord> getSalesHistory(String productId, LocalDate since);

    Optional<Double> getPurchaseCost(String productId);

    Optional<Double> getSellingPrice(String productId);

    Optional<LocalDate> getExpiryDate(String productId);
}

