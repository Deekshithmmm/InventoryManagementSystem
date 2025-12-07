package main.java.com.inventory.ai.model;

import java.time.LocalDate;

public class SalesRecord {

    private final String productId;
    private final LocalDate date;
    private final int quantity;

    public SalesRecord(String productId, LocalDate date, int quantity) {
        this.productId = productId;
        this.date = date;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getQuantity() {
        return quantity;
    }
}
