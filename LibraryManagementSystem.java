import java.sql.*;
import java.util.*;

class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

class Book {
    private int id;
    private String title;
    private String author;
    private boolean available;

    public Book(int id, String title, String author, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = available;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public boolean isAvailable() { return available; }

    @Override
    public String toString() {
        return id + " | " + title + " by " + author + " | " + (available ? "Available" : "Issued");
    }
}

class LibraryService {
    public void addBook(String title, String author) throws SQLException {
        String query = "INSERT INTO books (title, author, available) VALUES (?, ?, TRUE)";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.executeUpdate();
            System.out.println("✅ Book added successfully!");
        }
    }

    public void viewBooks() throws SQLException {
        String query = "SELECT * FROM books";
        try (Connection con = DBConnection.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            System.out.println("📚 Book List:");
            while (rs.next()) {
                Book book = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getBoolean("available"));
                System.out.println(book);
            }
        }
    }

    public void issueBook(int bookId, int userId) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            String check = "SELECT available FROM books WHERE id=?";
            PreparedStatement psCheck = con.prepareStatement(check);
            psCheck.setInt(1, bookId);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next() && rs.getBoolean("available")) {
                String issue = "INSERT INTO transactions (user_id, book_id, issue_date) VALUES (?, ?, CURDATE())";
                PreparedStatement ps = con.prepareStatement(issue);
                ps.setInt(1, userId);
                ps.setInt(2, bookId);
                ps.executeUpdate();

                String update = "UPDATE books SET available=FALSE WHERE id=?";
                PreparedStatement psUpdate = con.prepareStatement(update);
                psUpdate.setInt(1, bookId);
                psUpdate.executeUpdate();

                System.out.println("📖 Book issued successfully!");
            } else {
                System.out.println("⚠️ Book not available!");
            }
        }
    }

    public void returnBook(int bookId, int userId) throws SQLException {
        try (Connection con = DBConnection.getConnection()) {
            String update = "UPDATE books SET available=TRUE WHERE id=?";
            PreparedStatement ps = con.prepareStatement(update);
            ps.setInt(1, bookId);
            ps.executeUpdate();

            String returnBook = "UPDATE transactions SET return_date=CURDATE() WHERE book_id=? AND user_id=? AND return_date IS NULL";
            PreparedStatement psReturn = con.prepareStatement(returnBook);
            psReturn.setInt(1, bookId);
            psReturn.setInt(2, userId);
            psReturn.executeUpdate();

            System.out.println("✅ Book returned successfully!");
        }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LibraryService library = new LibraryService();

        while (true) {
            System.out.println("\n=== LIBRARY MENU ===");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        sc.nextLine();
                        System.out.print("Enter Book Title: ");
                        String title = sc.nextLine();
                        System.out.print("Enter Author: ");
                        String author = sc.nextLine();
                        library.addBook(title, author);
                        break;

                    case 2:
                        library.viewBooks();
                        break;

                    case 3:
                        System.out.print("Enter Book ID: ");
                        int bookId = sc.nextInt();
                        System.out.print("Enter User ID: ");
                        int userId = sc.nextInt();
                        library.issueBook(bookId, userId);
                        break;

                    case 4:
                        System.out.print("Enter Book ID: ");
                        int returnBookId = sc.nextInt();
                        System.out.print("Enter User ID: ");
                        int returnUserId = sc.nextInt();
                        library.returnBook(returnBookId, returnUserId);
                        break;

                    case 5:
                        System.out.println("👋 Exiting...");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Try again!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
