package org.terracotta.qa;

import org.coenraets.model.Wine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aurelien Broszniowski
 */
public class TestDbServer {

  public static void main(String[] args) {

    List<Wine> list = new ArrayList<Wine>();
    try {
      Class.forName("org.h2.Driver");
      Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost:8092/mem:db1", "sa", "");
      Statement s = conn.createStatement();
      ResultSet rs = s.executeQuery("select * from PUBLIC.WINE");
      while (rs.next()) {
        list.add(processRow(rs));
      }
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }

    for (Wine wine : list) {
      System.out.println("Wine => " + wine.toString());
    }

/*
    List<Wine> list = new ArrayList<Wine>();
    Connection c = null;
    String sql = "select * from PUBLIC.WINE";
//    String sql = "SELECT * FROM PUBLIC.WINE ORDER BY name";
    try {
      c = ConnectionHelper.getH2Connection();
      Statement s = c.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        list.add(processRow(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      ConnectionHelper.close(c);
    }

    for (Wine wine : list) {
      System.out.println("Wine => " + wine.toString());
    }*/
  }

  protected static Wine processRow(ResultSet rs) throws SQLException {
    Wine wine = new Wine();
    wine.setId(rs.getLong("id"));
    wine.setName(rs.getString("name"));
    wine.setGrapes(rs.getString("grapes"));
    wine.setCountry(rs.getString("country"));
    wine.setRegion(rs.getString("region"));
    wine.setYear(rs.getString("year"));
    wine.setPicture(rs.getString("picture"));
    wine.setDescription(rs.getString("description"));
    return wine;
  }

}
