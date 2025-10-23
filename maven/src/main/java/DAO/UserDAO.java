package DAO;

import DTO.UserDTO;
import Database.ConnectionFactory;
import UI.UsersPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Locale;
import java.util.Vector;

public class UserDAO {
    private Connection conn;
    private PreparedStatement prepStatement;
    private Statement statement;
    private ResultSet resultSet;

    // ✅ Constructor – Connect to DB once
    public UserDAO() {
        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ LOGIN CHECK
    public boolean checkLogin(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    // ✅ GET USER DETAILS AFTER LOGIN
    public UserDTO getUserDetails(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            UserDTO user = new UserDTO();
            user.setUsername(rs.getString("username"));
            user.setUserType(rs.getString("usertype"));  // OR role (if table has 'role')
            user.setFullName(rs.getString("name"));
            return user;
        }
        return null;
    }

    // ✅ ADD USER (existing code simplified)
    public void addUserDAO(UserDTO userDTO, String userType) {
        try {
            String query = "INSERT INTO users (name, location, phone, username, password, usertype) VALUES (?, ?, ?, ?, ?, ?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userDTO.getFullName());
            prepStatement.setString(2, userDTO.getLocation());
            prepStatement.setString(3, userDTO.getPhone());
            prepStatement.setString(4, userDTO.getUsername());
            prepStatement.setString(5, userDTO.getPassword());
            prepStatement.setString(6, userDTO.getUserType());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "User added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ UPDATE USER
    public void editUserDAO(UserDTO userDTO) {
        try {
            String query = "UPDATE users SET name=?, location=?, phone=?, usertype=? WHERE username=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userDTO.getFullName());
            prepStatement.setString(2, userDTO.getLocation());
            prepStatement.setString(3, userDTO.getPhone());
            prepStatement.setString(4, userDTO.getUserType());
            prepStatement.setString(5, userDTO.getUsername());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "User Updated Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ DELETE USER
    public void deleteUserDAO(String username) {
        try {
            String query = "DELETE FROM users WHERE username=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, username);
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "User Deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        new UsersPage().loadDataSet();
    }

    // ✅ LOAD DATA TABLE
    public ResultSet getQueryResult() {
        try {
            String query = "SELECT * FROM users";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // ✅ Build table model for JTable
    public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int colCount = metaData.getColumnCount();

        for (int col = 1; col <= colCount; col++) {
            columnNames.add(metaData.getColumnName(col).toUpperCase(Locale.ROOT));
        }

        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int col = 1; col <= colCount; col++) {
                vector.add(rs.getObject(col));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }
}