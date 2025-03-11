import java.sql.*;
import java.util.Scanner;

public class ProductCRUD 
{
    public static void main(String[] args) 
    {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "Sam";
        String password = "Sam@1234";
        try (Connection conn = DriverManager.getConnection(url, user, password); 
             Scanner scanner = new Scanner(System.in)) 
             {
            boolean exit = false;
            while (!exit) 
            {
                System.out.println("1. Add Product");
                System.out.println("2. View Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                switch (choice) 
                {
                    case 1:
                        addProduct(conn, scanner);
                        break;
                    case 2:
                        viewProducts(conn);
                        break;
                    case 3:
                        updateProduct(conn, scanner);
                        break;
                    case 4:
                        deleteProduct(conn, scanner);
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
    private static void addProduct(Connection conn, Scanner scanner) throws SQLException 
    {
        System.out.print("Enter Product Name: ");
        String name = scanner.next();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        String query = "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) 
        {
            conn.setAutoCommit(false);
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
            conn.commit();
            System.out.println("Product added successfully.");
        } 
        catch (SQLException e) 
        {
            conn.rollback();
            e.printStackTrace();
        }
    }
    private static void viewProducts(Connection conn) throws SQLException 
    {
        String query = "SELECT * FROM Product";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) 
        {
            while (rs.next()) 
            {
                int id = rs.getInt("ProductID");
                String name = rs.getString("ProductName");
                double price = rs.getDouble("Price");
                int quantity = rs.getInt("Quantity");
                System.out.println(id + " | " + name + " | " + price + " | " + quantity);
            }
        }
    }
    private static void updateProduct(Connection conn, Scanner scanner) throws SQLException 
    {
        System.out.print("Enter Product ID to update: ");
        int id = scanner.nextInt();
        System.out.print("Enter new Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter new Quantity: ");
        int quantity = scanner.nextInt();
        String query = "UPDATE Product SET Price = ?, Quantity = ? WHERE ProductID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) 
        {
            conn.setAutoCommit(false);
            pstmt.setDouble(1, price);
            pstmt.setInt(2, quantity);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            conn.commit();
            System.out.println("Product updated successfully.");
        } 
        catch (SQLException e) 
        {
            conn.rollback();
            e.printStackTrace();
        }
    }
    private static void deleteProduct(Connection conn, Scanner scanner) throws SQLException 
    {
        System.out.print("Enter Product ID to delete: ");
        int id = scanner.nextInt();
        String query = "DELETE FROM Product WHERE ProductID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) 
        {
            conn.setAutoCommit(false);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            conn.commit();
            System.out.println("Product deleted successfully.");
        } 
        catch (SQLException e) 
        {
            conn.rollback();
            e.printStackTrace();
        }
    }
}