import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class EventManager {

    // Method to fetch events from the EVENT table and insert them into the AVL tree
    public void fetchAndAddEventsFromDatabase(Connection conn, EventAVLTree eventTree, int userId) {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            // Prepare query to fetch events for the logged-in user
            String query = "SELECT event_name, location, event_date FROM Event WHERE userid = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            resultSet = stmt.executeQuery();

            // Loop through each event in the result set and add it to the AVL tree
            while (resultSet.next()) {
                String eventName = resultSet.getString("event_name");
                String location = resultSet.getString("location");
                String eventDate = resultSet.getString("event_date");

                // Add event from DB to AVL tree
                eventTree.addEvent(eventDate, eventName, location);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (stmt != null) stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


