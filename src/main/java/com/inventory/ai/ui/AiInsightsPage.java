package main.java.com.inventory.ai.ui;

import main.java.com.inventory.ai.InventoryChatBot;
import main.java.com.inventory.ai.adapter.MyDataAdapter;

import javax.swing.*;
import java.awt.*;

public class AiInsightsPage extends JPanel {

    private JTextArea outputArea;
    private JTextField inputField;
    private InventoryChatBot bot;

    public AiInsightsPage() {

        bot = new InventoryChatBot(new MyDataAdapter());

        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 250));   // Light background

        // ---- TITLE ----
        JLabel title = new JLabel("AI Insights & Inventory Chatbot", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setOpaque(true);
        title.setBackground(new Color(230, 230, 245));
        title.setForeground(new Color(60, 60, 80));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---- OUTPUT AREA ----
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        outputArea.setBackground(new Color(255, 255, 255)); // WHITE
        outputArea.setForeground(Color.BLACK);
        outputArea.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        // ---- INPUT PANEL ----
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        inputPanel.setBackground(new Color(245, 245, 255));

        inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        inputField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JButton sendButton = new JButton("Send");
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        sendButton.setBackground(new Color(70, 130, 180));
        sendButton.setForeground(Color.WHITE);

        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // ---- ADD COMPONENTS ----
        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void sendMessage() {
        String msg = inputField.getText().trim();
        if (msg.isEmpty()) return;

        outputArea.append("You: " + msg + "\n");

        String reply = bot.reply(msg);
        outputArea.append("AI: " + reply + "\n\n");

        inputField.setText("");
    }
}
