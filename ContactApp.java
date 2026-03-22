import java.sql.*;
import java.util.Scanner;

public class ContactApp {
    // 1. Database Credentials
    static final String URL = "jdbc:mysql://localhost:3306/contact_db";
    static final String USER = "root";
    static final String PASS = "root"; // <--- UPDATE THIS!

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            while (true) {
                System.out.println("\n--- CONTACT MANAGEMENT SYSTEM ---");
                System.out.println("1. Add New Contact");
                System.out.println("2. View All Contacts");
                System.out.println("3. Delete a Contact");
                System.out.println("4. Exit");
                System.out.print("Enter choice: ");

                int choice = sc.nextInt();
                sc.nextLine(); // Consume the newline

                if (choice == 4) {
                    System.out.println("Exiting Phonebook...");
                    break;
                }

                try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                    switch (choice) {
                        case 1:
                            System.out.print("Enter Name: ");
                            String name = sc.nextLine();
                            System.out.print("Enter Phone: ");
                            String phone = sc.nextLine();
                            System.out.print("Enter Email: ");
                            String email = sc.nextLine();
                            System.out.print("Enter Category (e.g., Work, Family): ");
                            String category = sc.nextLine();
                            addContact(conn, name, phone, email, category);
                            break;
                        case 2:
                            viewContacts(conn);
                            break;
                        case 3:
                            System.out.print("Enter the ID of the contact to delete: ");
                            int id = sc.nextInt();
                            deleteContact(conn, id);
                            break;
                        default:
                            System.out.println("[ERROR] Invalid choice!");
                    }
                } catch (SQLException e) {
                    System.out.println("[DATABASE ERROR] " + e.getMessage());
                }
            }
        } finally {
            sc.close(); // Closing scanner to prevent resource leak!
        }
    }

    // CREATE: Add a new contact
    public static void addContact(Connection conn, String name, String phone, String email, String category)
            throws SQLException {
        String sql = "INSERT INTO contacts (name, phone, email, category) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, phone);
        pstmt.setString(3, email);
        pstmt.setString(4, category);

        pstmt.executeUpdate();
        System.out.println("[SUCCESS] Contact saved successfully!");
    }

    // READ: Show all contacts
    public static void viewContacts(Connection conn) throws SQLException {
        String sql = "SELECT * FROM contacts ORDER BY name ASC";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("\n--- YOUR CONTACTS ---");
        System.out.printf("%-5s | %-15s | %-15s | %-20s | %-10s\n", "ID", "NAME", "PHONE", "EMAIL", "CATEGORY");
        System.out.println("-----------------------------------------------------------------------------");

        while (rs.next()) {
            System.out.printf("%-5d | %-15s | %-15s | %-20s | %-10s\n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("category"));
        }
    }

    // DELETE: Remove a contact by ID
    public static void deleteContact(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM contacts WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);

        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("[SUCCESS] Contact deleted!");
        } else {
            System.out.println("[ERROR] No contact found with that ID.");
        }
    }
}