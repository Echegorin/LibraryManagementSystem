import java.sql.*;
import java.util.Scanner;

public class StudentCRUD {
    static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    static final String USER = "root";
    static final String PASSWORD = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to database.");
            while (true) {
                System.out.println("\n1. Insert\n2. Retrieve\n3. Update\n4. Delete\n5. Exit");
                System.out.print("Choose option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter age: ");
                        int age = scanner.nextInt();
                        insertStudent(conn, name, age);
                        break;
                    case 2:
                        retrieveStudents(conn);
                        break;
                    case 3:
                        System.out.print("Enter ID to update: ");
                        int idU = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("New name: ");
                        String newName = scanner.nextLine();
                        System.out.print("New age: ");
                        int newAge = scanner.nextInt();
                        updateStudent(conn, idU, newName, newAge);
                        break;
                    case 4:
                        System.out.print("Enter ID to delete: ");
                        int idD = scanner.nextInt();
                        deleteStudent(conn, idD);
                        break;
                    case 5:
                        conn.close();
                        System.exit(0);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void insertStudent(Connection conn, String name, int age) throws SQLException {
        String sql = "INSERT INTO students(name, age) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setInt(2, age);
        stmt.executeUpdate();
        System.out.println("Student added!");
    }

    static void retrieveStudents(Connection conn) throws SQLException {
        String sql = "SELECT * FROM students";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("Students:");
        while (rs.next()) {
            System.out.println(rs.getInt("id") + " | " + rs.getString("name") + " | Age: " + rs.getInt("age"));
        }
    }

    static void updateStudent(Connection conn, int id, String name, int age) throws SQLException {
        String sql = "UPDATE students SET name=?, age=? WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setInt(2, age);
        stmt.setInt(3, id);
        stmt.executeUpdate();
        System.out.println("Student updated!");
    }

    static void deleteStudent(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        System.out.println("Student deleted!");
    }
}
