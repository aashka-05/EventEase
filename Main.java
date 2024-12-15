import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);
        EventAVLTree eventTree = new EventAVLTree();
        User user = new User();  // You are creating a user object here
        EventManager eventManager = new EventManager();

        try {
            // Establish database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nldsevent", "root", "root");

            // User login
            if (!user.login(conn, scanner)) {
                return;
            }

            // Fetch events from database for the logged-in user
            int userId = user.getUserId();  // Correctly call getUserId() using the user instance
            eventManager.fetchAndAddEventsFromDatabase(conn, eventTree, userId);

            boolean exit = false;
            while (!exit) {
                System.out.println("1. Add Event\n2. View Events\n3. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        // Get event details from user input
                        System.out.print("Enter event name: ");
                        String eventName = scanner.nextLine();
                        System.out.print("Enter location: ");
                        String location = scanner.nextLine();
                        System.out.print("Enter date (YYYY-MM-DD): ");
                        String eventDate = scanner.nextLine();

                        // Insert event into the database
                        String query = "INSERT INTO Event (userid, event_name, location, event_date) VALUES (?, ?, ?, ?)";
                        PreparedStatement stmt = conn.prepareStatement(query);
                        stmt.setInt(1, user.getUserId());  // Use the user instance to get the user ID
                        stmt.setString(2, eventName);
                        stmt.setString(3, location);
                        stmt.setString(4, eventDate);
                        stmt.executeUpdate();

                        // Add event to AVL tree
                        eventTree.addEvent(eventDate, eventName, location);
                        System.out.println("Event added successfully.");
                        break;

                    case 2:
                        // Display events date-wise from the AVL tree
                        System.out.println("Displaying events datewise:");
                        eventTree.displayEvents();
                        break;

                    case 3:
                        exit = true;
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid option, try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            scanner.close();
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
