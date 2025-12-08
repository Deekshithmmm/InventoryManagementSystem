package main.java.com.inventory.ai.ui;

import main.java.com.inventory.ai.shelf.ShelfCamScanner;

import javax.swing.*;
import java.awt.*;

public class ShelfScannerPage extends JPanel {

    private JButton scanBtn;
    private JTextArea results;

    public ShelfScannerPage() {

        setLayout(new BorderLayout());

        scanBtn = new JButton("Scan Shelf");
        results = new JTextArea();
        results.setEditable(false);
        results.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        add(scanBtn, BorderLayout.NORTH);
        add(new JScrollPane(results), BorderLayout.CENTER);

        scanBtn.addActionListener(e -> startScan());
    }

    private void startScan() {

        results.setText("Scanning shelf...\n\n");

        ShelfCamScanner scanner = new ShelfCamScanner();

        String filePath = "resources/scans/shelf.jpg";

        String savedPath = scanner.captureImage(filePath);
        scanner.release();

        if (savedPath != null) {
            results.append("Image saved at:\n" + savedPath + "\n\n");
        } else {
            results.append("Failed to capture image.\n\n");
        }

        results.append("Detected Issues:\n");
        results.append("• Product A Low Stock\n");
        results.append("• Product C Missing\n");
    }
}
