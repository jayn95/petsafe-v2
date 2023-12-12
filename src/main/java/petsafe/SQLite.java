package petsafe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLite {
  private Connection connect() {
    Connection conn = null;

    try {
      String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/src/main/petsafe.db";
      conn = DriverManager.getConnection(url);

      // System.out.println("Connection to sqlite has been established");

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

      if (conn == null) {
        throw new SQLException("Database connection error");
      }

      Statement stmt = conn.createStatement();
      rs = stmt.executeQuery(query);

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return rs;
  }

  public boolean addRow(String table, String[] columns, String[] values) {
    if (columns.length == values.length) {
      int arrayLength = values.length;
      
      List<String> questionMarks = new ArrayList<>();
      for (int i = 0; i < arrayLength; i++) {
        questionMarks.add("?");
      }
      
      String sql = "INSERT INTO " + table + "( " + String.join(", ", columns) + ") VALUES (" + String.join(", ", questionMarks) + ")";

      try (
        Connection conn = this.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        for (int i = 1; i <= arrayLength; i++) {
          stmt.setString(i, values[i-1]);
        }
        stmt.executeUpdate();

        return true;

      } catch (SQLException e) {
        // TODO: handle exception
        System.out.println(e.getMessage());
      }

    } else {
      throw new ArrayIndexOutOfBoundsException("Columns and Values are unequal in length");
    }

    return false;
  }
}
