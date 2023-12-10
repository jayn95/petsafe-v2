package petsafe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLite {
  private Connection connect() {
    Connection conn = null;

    try {
      String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/src/main/petsafe.db";
      conn = DriverManager.getConnection(url);

      System.out.println("Connection to sqlite has been established");

    } catch (SQLException e) {
      // TODO: handle exception
      System.out.println(e.getMessage());
    }

    return conn;
  }

  public ResultSet selectAll(String query) {
    ResultSet rs = null;

    try {
      Connection conn = this.connect();
      Statement stmt = conn.createStatement();
      rs = stmt.executeQuery(query);

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return rs;
  }
}
