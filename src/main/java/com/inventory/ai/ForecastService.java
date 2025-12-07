package main.java.com.inventory.ai;

import main.java.com.inventory.ai.adapter.DataAdapter;
import main.java.com.inventory.ai.model.SalesRecord;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class ForecastService {

    private final DataAdapter adapter;

    public ForecastService(DataAdapter adapter) {
        this.adapter = adapter;
    }

    public double forecastNextMonth(String productId, int lookbackDays) {
        List<SalesRecord> history =
                adapter.getSalesHistory(productId, LocalDate.now().minusDays(lookbackDays));

        if (history == null || history.isEmpty())
            return 0;

        history.sort(Comparator.comparing(SalesRecord::getDate));

        double num = 0, den = 0;
        for (int i = 0; i < history.size(); i++) {
            double weight = i + 1;
            num += history.get(i).getQuantity() * weight;
            den += weight;
        }

        return (num / den) * 30;
    }
}

