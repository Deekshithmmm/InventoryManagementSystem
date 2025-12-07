package main.java.com.inventory.ai.model;

import java.time.LocalDate;

public class SalesRecord {

    private final String productId;
    private final LocalDate date;
    private final int quantity;
    private final double amount;

    public SalesRecord(String productId, LocalDate date, int quantity,double amount) {
        this.productId = productId;
        this.date = date;
        this.quantity = quantity;
        this.amount= amount;
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
    @Override
    public String toString() {
        return String.format("📅 %s | Qty: %d | Amount: %.2f",
                date, quantity, amount);
    }

}
