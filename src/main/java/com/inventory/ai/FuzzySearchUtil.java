package main.java.com.inventory.ai;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

public class FuzzySearchUtil<T> {

    public interface NameExtractor<T> {
        String getName(T t);
    }

    private final NameExtractor<T> extractor;

    public FuzzySearchUtil(NameExtractor<T> extractor) {
        this.extractor = extractor;
    }

    public List<T> fuzzySearch(List<T> items, String query, int maxResults) {
        if (items == null || items.isEmpty()) return Collections.emptyList();

        String q = query.toLowerCase();

        PriorityQueue<Map.Entry<T, Double>> pq =
                new PriorityQueue<>(Comparator.comparingDouble(Map.Entry::getValue));

        for (T item : items) {
            String name = extractor.getName(item).toLowerCase();
            double score = computeScore(name, q);

            pq.offer(new AbstractMap.SimpleEntry<>(item, -score));
            if (pq.size() > maxResults)
                pq.poll();
        }

        List<T> result = new ArrayList<>();
        while (!pq.isEmpty()) result.add(pq.poll().getKey());

        Collections.reverse(result);
        return result;
    }

    private double computeScore(String name, String q) {
        if (name.contains(q)) return 100;

        int dist = StringUtils.getLevenshteinDistance(name, q);
        return 1 - (double) dist / Math.max(name.length(), q.length());
    }
}

