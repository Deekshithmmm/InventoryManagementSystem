package main.java.com.inventory.ai.ui;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import javax.swing.*;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.FlowLayout;

import java.awt.image.BufferedImage;
import java.awt.Font;

import java.io.File;
import javax.imageio.ImageIO;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;


public class ShelfScannerPage extends JPanel {

    private JButton refreshBtn;
    private JTextArea results;

    private Webcam webcam;
    private WebcamPanel webcamPanel;

    // AUTO SCANNING TIMER
    private Timer autoCaptureTimer;

    public ShelfScannerPage() {

        setLayout(new BorderLayout());

        initializeWebcam();

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        refreshBtn = new JButton("Refresh Camera");

        topBar.add(refreshBtn);
        add(topBar, BorderLayout.NORTH);

        results = new JTextArea();
        results.setEditable(false);
        results.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(results);
        scroll.setPreferredSize(new Dimension(300, 180));
        add(scroll, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> refreshCamera());

        // START CONTINUOUS AUTO SCANNING
        startAutomaticCapture();
    }

    // ======================================================
    // AUTO CAPTURE + LIVE CONTINUOUS COUNTING
    // ======================================================
    private void startAutomaticCapture() {

        autoCaptureTimer = new Timer(2000, e -> captureImageAutomatically());
        autoCaptureTimer.start();

        results.append("⏱ Continuous shelf scanning started (every 2 seconds)...\n\n");
    }


    private void captureImageAutomatically() {

        try {
            if (webcam == null || !webcam.isOpen()) {
                results.append("❌ Webcam not open. Skipping.\n");
                return;
            }

            BufferedImage img = webcam.getImage();
            if (img == null) {
                results.append("❌ Failed auto-capture.\n");
                return;
            }

            // Ensure folder exists
            File folder = new File("resources/scans");
            if (!folder.exists()) folder.mkdirs();

            // Save file
            String path = "resources/scans/auto_" + System.currentTimeMillis() + ".jpg";
            ImageIO.write(img, "JPG", new File(path));

            // Detect products continuously
            Map<String, Integer> detected = countProductsByColorGroups(img);

            results.setText(""); // clear output
            results.append("📸 Latest auto-captured image:\n" + path + "\n\n");

            results.append("📦 LIVE PRODUCT COUNTS:\n");

            for (String key : detected.keySet()) {
                results.append(key + " = " + detected.get(key) + "\n");
            }

            results.append("\n(Updated every 2 seconds automatically)\n");

        } catch (Exception ex) {
            ex.printStackTrace();
            results.append("❌ Auto-capture error.\n");
        }
    }

    // ======================================================
    // WEBCAM INITIALIZATION
    // ======================================================
    private void initializeWebcam() {

        if (webcamPanel != null)
            remove(webcamPanel);

        webcam = Webcam.getDefault();

        if (webcam != null) {
            webcam.setViewSize(new Dimension(640, 480));
            webcamPanel = new WebcamPanel(webcam);
            webcamPanel.setFPSDisplayed(true);
            webcamPanel.setFillArea(true);
            add(webcamPanel, BorderLayout.CENTER);

        } else {
            add(new JLabel("❌ No webcam detected!", SwingConstants.CENTER), BorderLayout.CENTER);
        }

        revalidate();
        repaint();
    }

    private void refreshCamera() {

        results.append("Refreshing camera...\n");

        try {
            if (webcam != null && webcam.isOpen())
                webcam.close();

        } catch (Exception ignored) {}

        webcam = null;
        initializeWebcam();

        results.append("Camera refreshed.\n\n");
    }


    // ======================================================
    // PRODUCT COUNTING (COLOR CLUSTERING)
    // ======================================================
    private Map<String, Integer> countProductsByColorGroups(BufferedImage img) {

        int width = img.getWidth();
        int height = img.getHeight();

        boolean[][] visited = new boolean[height][width];
        List<Color> objectColors = new ArrayList<>();

        int threshold = 120; // Darkness threshold

        // FIND BLOBS
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                if (!visited[y][x] && isObjectPixel(img, x, y, threshold)) {

                    Color avgColor = averageObjectColor(img, x, y, visited, threshold);
                    objectColors.add(avgColor);
                }
            }
        }

        // CLUSTER COLORS INTO PRODUCT GROUPS
        Map<String, Integer> productCounts = new HashMap<>();
        List<Color> centroids = new ArrayList<>();

        int index = 0;

        for (Color c : objectColors) {

            boolean matched = false;

            for (int i = 0; i < centroids.size(); i++) {

                if (colorDistance(c, centroids.get(i)) < 60) {

                    String name = "Product " + (char) ('A' + i);
                    productCounts.put(name, productCounts.get(name) + 1);
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                centroids.add(c);
                String name = "Product " + (char) ('A' + index++);
                productCounts.put(name, 1);
            }
        }

        return productCounts;
    }


    private double colorDistance(Color c1, Color c2) {
        int dr = c1.getRed() - c2.getRed();
        int dg = c1.getGreen() - c2.getGreen();
        int db = c1.getBlue() - c2.getBlue();
        return Math.sqrt(dr * dr + dg * dg + db * db);
    }

    private boolean isObjectPixel(BufferedImage img, int x, int y, int threshold) {
        Color c = new Color(img.getRGB(x, y));
        int gray = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
        return gray < threshold; // dark pixel = object
    }

    private Color averageObjectColor(BufferedImage img, int x, int y, boolean[][] visited, int threshold) {

        int width = img.getWidth();
        int height = img.getHeight();

        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(x, y));

        int sumR = 0, sumG = 0, sumB = 0, count = 0;

        while (!queue.isEmpty()) {

            Point p = queue.remove();

            int px = p.x, py = p.y;

            if (px < 0 || py < 0 || px >= width || py >= height)
                continue;

            if (visited[py][px])
                continue;

            if (!isObjectPixel(img, px, py, threshold))
                continue;

            visited[py][px] = true;

            Color c = new Color(img.getRGB(px, py));
            sumR += c.getRed();
            sumG += c.getGreen();
            sumB += c.getBlue();
            count++;

            queue.add(new Point(px + 1, py));
            queue.add(new Point(px - 1, py));
            queue.add(new Point(px, py + 1));
            queue.add(new Point(px, py - 1));
        }

        if (count == 0) return Color.BLACK;

        return new Color(sumR / count, sumG / count, sumB / count);
    }
}
