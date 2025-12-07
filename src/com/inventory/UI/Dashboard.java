package com.inventory.UI;
import main.java.com.inventory.ai.ui.AiInsightsPage;
import com.inventory.DAO.UserDAO;
import com.inventory.DTO.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;

public class Dashboard extends javax.swing.JFrame {

    CardLayout layout;
    String userSelect;
    String username;
    UserDTO userDTO;
    LocalDateTime outTime;

    // Add AI Button Reference
    private javax.swing.JButton aiButton;

    public Dashboard(String username, String userType, UserDTO userDTO) {
        initComponents();

        this.username = username;
        this.userSelect = userType;
        this.userDTO = userDTO;

        displayPanel.setLayout(layout = new CardLayout());

        // Register all pages
        displayPanel.add("Home", new HomePage(username));
        displayPanel.add("Users", new UsersPage());
        displayPanel.add("Customers", new CustomerPage());
        displayPanel.add("Products", new ProductPage(username, this));
        displayPanel.add("Suppliers", new SupplierPage());
        displayPanel.add("Current Stock", new CurrentStockPage(username));
        displayPanel.add("Sales", new SalesPage(username, this));
        displayPanel.add("Purchase", new PurchasePage(this));
        displayPanel.add("Logs", new UserLogsPage());
        displayPanel.add("AI", new AiInsightsPage());   // <-- NEW AI PAGE

        if ("EMPLOYEE".equalsIgnoreCase(userType)) {
            notForEmployee();
        }

        currentUserSession();

        // Save log on close
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                outTime = LocalDateTime.now();
                userDTO.setOutTime(String.valueOf(outTime));
                userDTO.setUsername(username);
                new UserDAO().addUserLogin(userDTO);
                super.windowClosing(e);
            }
        });

        setTitle("Inventory Manager");
        setVisible(true);
    }

    // ---------------- PAGE SWITCHERS -------------------
    public void addHomePage() { layout.show(displayPanel, "Home"); }
    public void addUsersPage() { layout.show(displayPanel, "Users"); }
    public void addCustPage() { layout.show(displayPanel, "Customers"); }
    public void addProdPage() { layout.show(displayPanel, "Products"); }
    public void addSuppPage() { layout.show(displayPanel, "Suppliers"); }
    public void addStockPage() { layout.show(displayPanel, "Current Stock"); }
    public void addSalesPage() { layout.show(displayPanel, "Sales"); }
    public void addPurchasePage() { layout.show(displayPanel, "Purchase"); }
    public void addLogsPage() { layout.show(displayPanel, "Logs"); }

    // NEW — Show AI Panel
    public void addAiPage() { layout.show(displayPanel, "AI"); }

    // -----------------------------------------------
    @SuppressWarnings("unchecked")
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        menuPanel = new javax.swing.JPanel();
        menuButton = new javax.swing.JButton();
        navPanel = new javax.swing.JPanel();

        homeButton = new javax.swing.JButton();
        prodButton = new javax.swing.JButton();
        stockButton = new javax.swing.JButton();
        custButton = new javax.swing.JButton();
        suppButton = new javax.swing.JButton();
        salesButton = new javax.swing.JButton();
        usersButton = new javax.swing.JButton();
        purchaseButton = new javax.swing.JButton();
        logsButton = new javax.swing.JButton();

        // NEW AI BUTTON
        aiButton = new javax.swing.JButton();

        displayPanel = new javax.swing.JPanel();
        userPanel = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(400, 100, 0, 0));

        // ------------ MENU BUTTON ------------
        menuButton.setText("MENU");
        menuButton.addActionListener(evt -> menuButtonActionPerformed(evt));

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
                menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(menuButton, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
        );
        menuPanelLayout.setVerticalGroup(
                menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(menuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        // ----------------- NAV BUTTONS -------------------
        homeButton.setText("Home");
        homeButton.addActionListener(evt -> addHomePage());

        prodButton.setText("Products");
        prodButton.addActionListener(evt -> addProdPage());

        stockButton.setText("Current Stock");
        stockButton.addActionListener(evt -> addStockPage());

        custButton.setText("Customers");
        custButton.addActionListener(evt -> addCustPage());

        suppButton.setText("Suppliers");
        suppButton.addActionListener(evt -> addSuppPage());

        salesButton.setText("Sales");
        salesButton.addActionListener(evt -> addSalesPage());

        usersButton.setText("Users");
        usersButton.addActionListener(evt -> addUsersPage());

        purchaseButton.setText("Purchase");
        purchaseButton.addActionListener(evt -> addPurchasePage());

        logsButton.setText("User Logs");
        logsButton.addActionListener(evt -> addLogsPage());

        // ------------------ NEW AI BUTTON -------------------
        aiButton.setText("AI Insights");
        aiButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        aiButton.addActionListener(evt -> addAiPage());

        // ------------------ NAV PANEL LAYOUT -------------------
        javax.swing.GroupLayout navPanelLayout = new javax.swing.GroupLayout(navPanel);
        navPanel.setLayout(navPanelLayout);
        navPanelLayout.setHorizontalGroup(
                navPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(navPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(navPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(homeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                        .addComponent(prodButton)
                                        .addComponent(stockButton)
                                        .addComponent(custButton)
                                        .addComponent(suppButton)
                                        .addComponent(salesButton)
                                        .addComponent(purchaseButton)
                                        .addComponent(usersButton)
                                        .addComponent(logsButton)
                                        .addComponent(aiButton))   // <-- AI BUTTON ADDED HERE
                                .addContainerGap())
        );

        navPanelLayout.setVerticalGroup(
                navPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(navPanelLayout.createSequentialGroup()
                                .addGap(10)
                                .addComponent(homeButton, 35, 35, 35)
                                .addGap(10)
                                .addComponent(prodButton, 35, 35, 35)
                                .addGap(10)
                                .addComponent(stockButton, 35, 35, 35)
                                .addGap(10)
                                .addComponent(custButton, 35, 35, 35)
                                .addGap(10)
                                .addComponent(suppButton, 35, 35, 35)
                                .addGap(10)
                                .addComponent(salesButton, 35, 35, 35)
                                .addGap(10)
                                .addComponent(purchaseButton, 35, 35, 35)
                                .addGap(10)
                                .addComponent(usersButton, 35, 35, 35)
                                .addGap(10)
                                .addComponent(logsButton, 35, 35, 35)
                                .addGap(10)
                                .addComponent(aiButton, 35, 35, 35)  // <-- AI BUTTON POSITION
                                .addContainerGap())
        );

        // ----------------- USER PANEL -------------------
        nameLabel.setText("User:");
        logoutButton.setText("Sign out");
        logoutButton.addActionListener(evt -> logoutButtonActionPerformed(evt));

        javax.swing.GroupLayout userPanelLayout = new javax.swing.GroupLayout(userPanel);
        userPanel.setLayout(userPanelLayout);
        userPanelLayout.setHorizontalGroup(
                userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, userPanelLayout.createSequentialGroup()
                                .addContainerGap(450, Short.MAX_VALUE)
                                .addComponent(nameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(logoutButton)
                                .addContainerGap())
        );
        userPanelLayout.setVerticalGroup(
                userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(nameLabel, 35, 35, 35)
                                .addComponent(logoutButton, 35, 35, 35))
        );

        // -------------- MAIN LAYOUT --------------------
        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(menuPanel, 125, 125, 125)
                                        .addComponent(navPanel, 125, 125, 125))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(userPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(userPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(navPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        getContentPane().add(mainPanel);
        pack();
    }

    // ------------------- LOGOUT ------------------------
    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int opt = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to logout?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (opt == JOptionPane.YES_OPTION) {
            outTime = LocalDateTime.now();
            userDTO.setOutTime(String.valueOf(outTime));
            userDTO.setUsername(username);
            new UserDAO().addUserLogin(userDTO);

            dispose();
            new LoginPage().setVisible(true);
        }
    }

    private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {
        navPanel.setVisible(!navPanel.isVisible());
    }

    // Hide admin-only buttons
    public void notForEmployee() {
        navPanel.remove(usersButton);
        navPanel.remove(logsButton);
    }

    // Load logged user name
    public void currentUserSession() {
        UserDTO u = new UserDTO();
        new UserDAO().getFullName(u, username);
        nameLabel.setText("User: " + u.getFullName() + " (" + userSelect + ")");
    }

    // Variables declaration
    private javax.swing.JPanel displayPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JButton menuButton;
    private javax.swing.JPanel navPanel;
    private javax.swing.JPanel userPanel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton homeButton, prodButton, stockButton, custButton, suppButton,
            salesButton, usersButton, purchaseButton, logsButton;
}
