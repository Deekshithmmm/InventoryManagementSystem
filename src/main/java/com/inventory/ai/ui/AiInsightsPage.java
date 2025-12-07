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
        setBackground(new Color(245, 245, 255));

        // ---- TITLE ----
        JLabel title = new JLabel("AI Insights & Inventory Chatbot", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---- OUTPUT AREA ----
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // ---- INPUT PANEL ----
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        JButton sendButton = new JButton("Send");

        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage()); // ENTER key support

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
