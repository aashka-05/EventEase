import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private int userId;
    private String username;

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    // User login method
    public boolean login(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String query = "SELECT userid FROM Users WHERE username = ? AND password = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            this.userId = rs.getInt("userid");
            this.username = username;
            System.out.println("Login successful.");
            return true;
        } else {
            System.out.println("Login failed. Please try again.");
            return false;
        }
    }
}
