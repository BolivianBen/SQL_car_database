package chap34;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class tester {
	static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
	
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/pcparts", "root", "sesame");
            System.out.println("Database connected");
        } catch (SQLException e) {
            System.out.println("Cannot connect");
        }
        try {
            java.sql.Statement st = conn.createStatement();

            System.out.println("Enter fields separated by commas or Enter for all fields");
            String fields = input.nextLine();
            if(fields.equals("")) {
            	fields = "custid,name,address,city,state,zip";
            }
            System.out.println("Enter where and order by clauses or Enter for none");
            String mods = input.nextLine();            
            String query = "SELECT " + fields + " FROM customers " + mods;
            System.out.println(query);
            java.sql.ResultSet rSet = st.executeQuery(query);
            ResultSetMetaData rsMetaData = rSet.getMetaData();
            for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
              System.out.printf("%-12s\t", rsMetaData.getColumnName(i));
            System.out.println();
            
            // Iterate through the result and print the student names
            while (rSet.next()) {
              for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
                System.out.printf("%-12s\t", rSet.getObject(i));
              System.out.println();
            }
        } catch (SQLException ex) {
            // handle the error (rather badly)
            System.out.println("OOPS...");
            System.out.println(ex.getMessage());
        }
    }	
}
