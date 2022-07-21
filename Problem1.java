package chap34;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

///This program is a combination of materials in the textbook and the zipped programs CustomersSQL.java and SQLVehicle.java.

///Please note that I had to connect to localhost:3307 instea     d of standard 3306 due to how MariaDB was initially installed on my system.

public class Problem1 {
	static Scanner input = new Scanner(System.in);
	
  public static void main(String[] args)
      throws SQLException, ClassNotFoundException {

    // I must connect to the example database within the bin
    Connection connection = DriverManager.getConnection
      ("jdbc:mysql://localhost:3307/example", "root", "sesame");
    System.out.println("Database connected");

    // Create a statement
    Statement statement = connection.createStatement();
    
    ///insert the new data for 2 additional cars. HOWEVER, If I ran the code more than once, it would give an error that I already had
    ///a vin with the same number. To avoid this error, I delete the VINs right before I make them, just in case they are already
    ///part of the table from the last time that I ran the code.
    statement.executeUpdate("DELETE from product where vin = 111111");
    statement.executeUpdate("DELETE from product where vin = 999999");
    
    ///Now I actually add the cars. The two best cars in the world, a tesla and a 69' black impala
    statement.executeUpdate("INSERT into product (vin, make, model, year, color, price) " + "values ('999999','TESLA','Model 3',2022,'Green',65000.00)");
    statement.executeUpdate("INSERT into product (vin, make, model, year, color, price) " + "values ('111111','Chevy','Impala',1969,'Black',25500.00)");

    ///ensuring connection to database.
    Connection conn = null;
    try {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/example", "root", "sesame");
    } catch (SQLException e) {
        System.out.println("Cannot connect");
    }
    try {
        java.sql.Statement st = conn.createStatement();

        ///request that the user inputs the fields they want to serach. If none are entered, a search for all fields will occur.
        System.out.println("Please input which specific fields you'd like to search ");
        System.out.println("(options: vin, make, model, year, color, price)");
        String fields = input.nextLine();
        if(fields.equals("")) {
        	fields = "vin, make, model, year, color, price ";
        }
        
        //Request the user for where and order by clauses (from zipped Customers.java).
        System.out.println("Enter where and order by clauses or Enter for none");
        System.out.println("(example: where price > 25000 order by price ASC)");
        String clauses = input.nextLine();            
        String SQLquery = "SELECT " + fields + " FROM product " + clauses;
        System.out.println(SQLquery);
        java.sql.ResultSet rSet = st.executeQuery(SQLquery);
        ResultSetMetaData rsMetaData = rSet.getMetaData();
        
        ///iterate the query in a for loop
        for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
          System.out.printf("%-12s\t", rsMetaData.getColumnName(i));
        System.out.println();
        
        // Iterate the results and print off all the requested information on the cars.
        while (rSet.next()) {
          for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
            System.out.printf("%-12s\t", rSet.getObject(i));
          System.out.println();
        }
    } catch (SQLException ex) {
        // If there is an error, report the exception to the users
        System.out.println("Oh man!! Lets see what went wrong...");
        System.out.println(ex.getMessage());
    }

 
    // Close the connection
    connection.close();
  }
}

