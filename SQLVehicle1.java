//  SQLVehicle.java

package chap34;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLVehicle1 {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/example", "root", "sesame");
            System.out.println("Database connected");
        } catch (SQLException e) {
            System.out.println("Cannot connect");
        }
        try {
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rSet = st.executeQuery("select * from product");
            while (rSet.next())  {
                System.out.println(rSet.getString(1) + " " 
                				 + rSet.getString(2) + " " 
                				 + rSet.getString(3) + " "
                				 + rSet.getString(4) + " "
                				 + rSet.getString(5) + " $"
                				 + rSet.getString(6));
            }
        } catch (SQLException ex) {
            // handle the error (rather badly)
            System.out.println("OOPS...");
            System.out.println(ex.getMessage());
        }
    }
}
