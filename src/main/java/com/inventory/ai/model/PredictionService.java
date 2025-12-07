package main.java.com.inventory.ai.model;

import main.java.com.inventory.ai.adapter.DataAdapter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public class PredictionService {

    private final DataAdapter dataAdapter;

    public PredictionService(DataAdapter dataAdapter) {
        this.dataAdapter = dataAdapter;
    }

    public OptionalDouble computeAvgDailyUsage(String productId, int daysBack) {
        LocalDate since = LocalDate.now().minusDays(daysBack);
        List<SalesRecord> history = dataAdapter.getSalesHistory(productId, since);

        if (history == null || history.isEmpty())
            return OptionalDouble.empty();

        long total = history.stream().mapToLong(SalesRecord::getQuantity).sum();
        long spanDays = ChronoUnit.DAYS.between(history.get(0).getDate(), LocalDate.now());
        spanDays = Math.max(spanDays, 1);

        return OptionalDouble.of((double) total / spanDays);
    }

    public Optional<Double> predictDaysLeft(String productId, int daysBack) {
        int stock = dataAdapter.getCurrentStock(productId);
        if (stock <= 0) return Optional.empty();


        OptionalDouble avg = computeAvgDailyUsage(productId, daysBack);
        if (avg.isEmpty() || avg.getAsDouble() <= 0)
            return Optional.empty();

        double daysLeft = stock / avg.getAsDouble();

        return Optional.of(daysLeft);
    }
}

