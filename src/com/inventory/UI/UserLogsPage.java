package com.inventory.UI;

import com.inventory.DAO.UserDAO;
import main.java.com.inventory.ai.ui.AiInsightsPage;   // <-- IMPORTANT (Adjust package if needed)

import java.sql.SQLException;

public class UserLogsPage extends javax.swing.JPanel {

    public UserLogsPage() {
        initComponents();
        loadDataSet();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        logTable = new javax.swing.JTable();
        refreshButton = new javax.swing.JButton();
        searchText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        aiButton = new javax.swing.JButton();   // <-- AI Button added

        jLabel1.setFont(new java.awt.Font("Impact", 0, 24));
        jLabel1.setText("USER LOGS");

        logTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        jScrollPane1.setViewportView(logTable);

        refreshButton.setFont(new java.awt.Font("Segoe UI", 1, 12));
        refreshButton.setText("REFRESH");
        refreshButton.addActionListener(evt -> refreshButtonActionPerformed(evt));

        searchText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTextKeyReleased(evt);
            }
        });

        jLabel2.setText("Search:");

        // ---------------- AI BUTTON --------------------
        aiButton.setFont(new java.awt.Font("Segoe UI", 1, 12));
        aiButton.setText("AI INSIGHTS");
        aiButton.addActionListener(evt -> openAIPage());
        // ------------------------------------------------

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator1)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)

                                                // ---------- RIGHT SIDE (Search + Refresh + AI BUTTON) ----------
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(aiButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        ))
                                .addContainerGap())
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()

                                // ---------- TOP ROW ----------
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2)
                                        .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(aiButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                )

                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)

                                // ---------- TABLE ----------
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(20, Short.MAX_VALUE))
        );
    }

    private void openAIPage() {
        new AiInsightsPage().setVisible(true);
    }

    private void searchTextKeyReleased(java.awt.event.KeyEvent evt) {}

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {
        loadDataSet();
    }

    public void loadDataSet() {
        try {
            UserDAO userDAO = new UserDAO();
            logTable.setModel(userDAO.buildTableModel(userDAO.getUserLogsDAO()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // UI Variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable logTable;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTextField searchText;

    // NEW AI BUTTON
    private javax.swing.JButton aiButton;

}
