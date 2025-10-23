import java.sql.Connection;
import java.sql.DriverManager;

public class TestDBConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/inventory"; // change database name if needed
        String user = "root"; // change if different
        String password = "Deekshith@12345"; // your MySQL password here

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Database Connected Successfully!");
            conn.close();
        } catch (Exception e) {
            System.out.println("❌ Connection Failed!");
            e.printStackTrace();
        }
    }
}
