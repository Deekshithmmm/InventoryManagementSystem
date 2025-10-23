package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // ✅ Replace with your actual database name, username, and password
    private static final String URL = "jdbc:mysql://localhost:3306/InventoryDB"; // Database name
    private static final String USER = "root";                                   // MySQL username
    private static final String PASSWORD = "Deekshith@12345";                    // MySQL password

    // ✅ Method to get database connection
    public static Connection getConnection() {
        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Return a live database connection
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            // If error occurs, print it and return null
            e.printStackTrace();
            return null;
        }
    }}
