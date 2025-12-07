package main.java.com.inventory.ai.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Central reusable UI color theme for the entire application.
 */
public class UITheme {

    // MAIN BRAND COLORS
    public static final Color PRIMARY = new Color(52, 152, 219);      // Blue
    public static final Color PRIMARY_DARK = new Color(41, 128, 185);

    public static final Color ACCENT = new Color(46, 204, 113);       // Green
    public static final Color WARNING = new Color(241, 196, 15);      // Yellow
    public static final Color DANGER = new Color(231, 76, 60);        // Red

    // BACKGROUND COLORS
    public static final Color BG_DARK = new Color(33, 33, 33);
    public static final Color BG_LIGHT = new Color(245, 245, 245);

    // TEXT COLORS
    public static final Color TEXT_LIGHT = Color.WHITE;
    public static final Color TEXT_DARK = new Color(44, 62, 80);

    // CARD / PANEL COLORS
    public static final Color PANEL_DARK = new Color(45, 45, 45);
    public static final Color PANEL_LIGHT = new Color(255, 255, 255);

    // SIDEBAR COLORS
    public static final Color SIDEBAR_BG = new Color(30, 30, 30);
    public static final Color SIDEBAR_HOVER = new Color(60, 60, 60);
    public static final Color SIDEBAR_SELECTED = PRIMARY;

    /**
     * Apply standard button styling
     */
    public static void styleButton(JButton btn) {
        btn.setBackground(PRIMARY);
        btn.setForeground(TEXT_LIGHT);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    /**
     * Apply selected menu button styling
     */
    public static void styleSidebarButton(JButton btn, boolean selected) {
        if (selected) {
            btn.setBackground(SIDEBAR_SELECTED);
            btn.setForeground(TEXT_LIGHT);
        } else {
            btn.setBackground(SIDEBAR_BG);
            btn.setForeground(TEXT_LIGHT);
        }
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
    }

    /**
     * Style top header title
     */
    public static void styleHeader(JLabel lbl) {
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lbl.setForeground(PRIMARY_DARK);
    }

    /**
     * Style chatbot message area
     */
    public static void styleChatArea(JTextArea area) {
        area.setFont(new Font("Consolas", Font.PLAIN, 14));
        area.setBackground(Color.WHITE);
        area.setForeground(Color.DARK_GRAY);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
    }

    /**
     * Style chatbot input field
     */
    public static void styleChatInput(JTextField text) {
        text.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        text.setBackground(Color.WHITE);
        text.setForeground(Color.BLACK);
        text.setBorder(BorderFactory.createLineBorder(PRIMARY_DARK, 2));
    }
}
