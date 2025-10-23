package UI;

import DAO.UserDAO;
import DTO.UserDTO;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDarkerIJTheme;
import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class LoginPage extends JFrame {

    private JTextField userText;
    private JPasswordField passText;
    private JButton loginButton;

    public LoginPage() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Login - Inventory Management System");
        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 70, 100, 30);
        add(userLabel);

        userText = new JTextField();
        userText.setBounds(150, 70, 180, 30);
        add(userText);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 120, 100, 30);
        add(passLabel);

        passText = new JPasswordField();
        passText.setBounds(150, 120, 180, 30);
        add(passText);

        loginButton = new JButton("Login");
        loginButton.setBounds(150, 170, 100, 30);
        add(loginButton);

        loginButton.addActionListener(e -> loginAction());
    }

    private void loginAction() {
        String username = userText.getText();
        String password = new String(passText.getPassword());

        if (username.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(this, "Enter both Username and Password");
        } else {
            try {
                UserDAO userDAO = new UserDAO();
                boolean status = userDAO.checkLogin(username, password);

                if (status) {
                    UserDTO user = userDAO.getUserDetails(username);
                    JOptionPane.showMessageDialog(this, "Login Successful!");

                    new Dashboard(user.getUsername(), user.getUserType(), user).setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Credentials");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        FlatMaterialDarkerIJTheme.setup();
        new LoginPage().setVisible(true);
    }
}