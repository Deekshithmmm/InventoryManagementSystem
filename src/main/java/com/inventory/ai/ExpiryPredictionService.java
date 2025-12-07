package main.java.com.inventory.ai;

import main.java.com.inventory.ai.adapter.DataAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpiryPredictionService {

    private final DataAdapter adapter;

    public ExpiryPredictionService(DataAdapter adapter) {
        this.adapter = adapter;
    }

    public List<String> getExpiringProducts(int days) {
        List<String> list = new ArrayList<>();

        for (String id : adapter.listAllProductIds()) {
            adapter.getExpiryDate(id).ifPresent(exp -> {
                long diff = LocalDate.now().until(exp).getDays();
                if (diff >= 0 && diff <= days)
                    list.add(id);
            });
        }

        return list;
    }
}
