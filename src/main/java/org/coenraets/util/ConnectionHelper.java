package org.coenraets.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
  private static ConnectionHelper instance;

  private ConnectionHelper() {

  }

  public static Connection getConnection() throws SQLException {
    if (instance == null) {
      instance = new ConnectionHelper();
    }
    try {
      Class.forName("com.mysql.jdbc.Driver");
      return DriverManager.getConnection(
          "jdbc:mysql://localhost/wine?user=root");
    } catch (SQLException e) {
      throw e;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      return null;
    }
  }

  public static void close(Connection connection) {
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}