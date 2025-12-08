package main.java.com.inventory.ai.shelf;

import java.util.ArrayList;
import java.util.List;

public class ShelfAnalyzer {

    // In future replace this with real AI model inference
    public List<String> analyzeShelf(String imagePath) {

        // Dummy predictions
        List<String> missingItems = new ArrayList<>();
        missingItems.add("Product A - Low Stock");
        missingItems.add("Product C - Missing");

        return missingItems;
    }
}
