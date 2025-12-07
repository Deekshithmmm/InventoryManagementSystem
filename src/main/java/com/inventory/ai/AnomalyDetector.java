package main.java.com.inventory.ai;

import main.java.com.inventory.ai.adapter.DataAdapter;
import main.java.com.inventory.ai.model.SalesRecord;

import java.time.LocalDate;
import java.util.List;

public class AnomalyDetector {

    private final DataAdapter adapter;

    public AnomalyDetector(DataAdapter adapter) {
        this.adapter = adapter;
    }

    public boolean isAnomalousRemoval(String productId, int qty, int lookback, double sensitivity) {
        List<SalesRecord> history =
                adapter.getSalesHistory(productId, LocalDate.now().minusDays(lookback));

        if (history == null || history.isEmpty())
            return false;

        double avg = history.stream().mapToInt(SalesRecord::getQuantity).average().orElse(0);
        return qty > avg * sensitivity;
    }
}

