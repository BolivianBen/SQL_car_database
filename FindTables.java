package chap34;

import java.sql.*;

public class FindTables {
  public static void main(String[] args)
      throws SQLException, ClassNotFoundException {

    // Connect to a database
    Connection connection = DriverManager.getConnection
      ("jdbc:mysql://localhost:3307/javabook", "root", "sesame");
    System.out.println("Database connected");

    DatabaseMetaData dbMetaData = connection.getMetaData();

    ResultSet rsTables = dbMetaData.getTables(null, null, null, new String[] {"TABLE"});
    System.out.println("User tables:");
    
    int count = 0;   /// use to print a newline after 6 tables
    while (rsTables.next()) {
      System.out.printf("%15s",rsTables.getString("TABLE_NAME"));
      count++;
      if(count % 6 == 0) {
    	  System.out.println();
      }
    }
    

    // Close the connection
    connection.close();
  }
}