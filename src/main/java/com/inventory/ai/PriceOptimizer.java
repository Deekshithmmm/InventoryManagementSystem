package main.java.com.inventory.ai;

import main.java.com.inventory.ai.adapter.DataAdapter;

import java.util.Optional;

public class PriceOptimizer {

    private final DataAdapter adapter;

    public PriceOptimizer(DataAdapter adapter) {
        this.adapter = adapter;
    }

    public Optional<Double> recommendPrice(String productId, double demandScore, double marginFactor) {
        Optional<Double> cost = adapter.getPurchaseCost(productId);
        if (cost.isEmpty()) return Optional.empty();

        double recommended = cost.get() + (demandScore * marginFactor * cost.get());
        return Optional.of(recommended);
    }
}
