package main.java.com.inventory.ai.model;

import java.time.LocalDate;

public class SalesRecord {

    private LocalDate date;
    private int quantity;
    private double revenue;

    public SalesRecord(LocalDate date, int quantity, double revenue) {
        this.date = date;
        this.quantity = quantity;
        this.revenue = revenue;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getRevenue() {
        return revenue;
    }
}
