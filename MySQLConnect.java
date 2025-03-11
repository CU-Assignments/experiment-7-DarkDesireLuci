import java.sql.*;
public class MySQLConnect 
{
    public static void main(String[] args) 
    {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "Sam";
        String password = "Sam@1234";
        try 
        {
            Connection conn = DriverManager.getConnection(url, user, password);
            String query = "SELECT EmpID, Name, Salary FROM Employee";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) 
            {
                int empId = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                System.out.println(empId + " | " + name + " | " + salary);
            }
            rs.close();
            stmt.close();
            conn.close();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
}