package main.java.com.inventory.ai.adapter;

import main.java.com.inventory.ai.model.SalesRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;              // important for toLocalDate()
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyDataAdapter implements DataAdapter {

    private Connection connect() throws Exception {
        String url = "jdbc:mysql://localhost:3306/inventory";
        String username = "root";
        String password = "Deekshith@12345";  // change if needed
        return DriverManager.getConnection(url, username, password);
    }

    // -------------------------------------------------------------
    // 1. List All Product IDs
    // -------------------------------------------------------------
    @Override
    public List<String> listAllProductIds() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT productcode FROM products";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getString("productcode"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // -------------------------------------------------------------
    // 2. Get Product Name
    // -------------------------------------------------------------
    @Override
    public Optional<String> getProductName(String productId) {
        String sql = "SELECT productname FROM products WHERE productcode = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(rs.getString("productname"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    // -------------------------------------------------------------
    // 3. Get Current Stock (from currentstock table)
    // -------------------------------------------------------------
    @Override
    public Optional<Integer> getCurrentStock(String productId) {
        String sql = "SELECT quantity FROM currentstock WHERE productcode = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(rs.getInt("quantity"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    // -------------------------------------------------------------
    // 4. Get Sales History (using DATE2 column)
    // -------------------------------------------------------------
    @Override
    public List<SalesRecord> getSalesHistory(String productId, LocalDate since) {
        List<SalesRecord> list = new ArrayList<>();

        String sql = "SELECT date2, quantity, revenue FROM salesinfo WHERE productcode = ? AND date2 >= ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productId);
            stmt.setDate(2, Date.valueOf(since));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Date sqlDate = rs.getDate("date2");
                int qty = rs.getInt("quantity");
                double revenue = rs.getDouble("revenue");

                list.add(new SalesRecord(sqlDate.toLocalDate(), qty, revenue));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // -------------------------------------------------------------
    // 5. Purchase Cost (from products table)
    // -------------------------------------------------------------
    @Override
    public Optional<Double> getPurchaseCost(String productId) {
        String sql = "SELECT costprice FROM products WHERE productcode = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(rs.getDouble("costprice"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    // -------------------------------------------------------------
    // 6. Selling Price (from products table)
    // -------------------------------------------------------------
    @Override
    public Optional<Double> getSellingPrice(String productId) {
        String sql = "SELECT sellprice FROM products WHERE productcode = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(rs.getDouble("sellprice"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    // -------------------------------------------------------------
    // 7. Expiry Date — You must add column expirydate to products table
    // -------------------------------------------------------------
    @Override
    public Optional<LocalDate> getExpiryDate(String productId) {
        String sql = "SELECT expirydate FROM products WHERE productcode = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Date sqlDate = rs.getDate("expirydate");
                if (sqlDate != null) {
                    return Optional.of(sqlDate.toLocalDate());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
