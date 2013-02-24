package org.coenraets.service;

import org.coenraets.model.Wine;
import org.coenraets.util.ConnectionHelper;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Christophe Coenraets
 */
@Service
public class WineMysql implements WineService {

  @Override
  public List<Wine> findAll() {
    List<Wine> list = new ArrayList<Wine>();
    Connection c = null;
    String sql = "SELECT * FROM wine ORDER BY name";
    try {
      c = ConnectionHelper.getConnection();
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
    return list;
  }


  @Override
  public List<Wine> findByName(String name) {
    List<Wine> list = new ArrayList<Wine>();
    Connection c = null;
    String sql = "SELECT * FROM wine as e " +
                 "WHERE UPPER(name) LIKE ? " +
                 "ORDER BY name";
    try {
      c = ConnectionHelper.getConnection();
      PreparedStatement ps = c.prepareStatement(sql);
      ps.setString(1, "%" + name.toUpperCase() + "%");
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        list.add(processRow(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      ConnectionHelper.close(c);
    }
    return list;
  }

  @Override
  public Wine findById(long id) {
    String sql = "SELECT * FROM wine WHERE id = ?";
    Wine wine = null;
    Connection c = null;
    try {
      c = ConnectionHelper.getConnection();
      PreparedStatement ps = c.prepareStatement(sql);
      ps.setLong(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        wine = processRow(rs);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      ConnectionHelper.close(c);
    }
    return wine;
  }

  @Override
  public Wine save(Wine wine) {
     throw new RuntimeException("not implemented");
  }

  @Override
  public Wine create(Wine wine) {
    Connection c = null;
    PreparedStatement ps = null;
    try {
      c = ConnectionHelper.getConnection();
      ps = c.prepareStatement("INSERT INTO wine (name, grapes, country, region, year, picture, description,id) VALUES (?, ?, ?, ?, ?, ?, ?,?)");
      ps.setString(1, wine.getName());
      ps.setString(2, wine.getGrapes());
      ps.setString(3, wine.getCountry());
      ps.setString(4, wine.getRegion());
      ps.setString(5, wine.getYear());
      ps.setString(6, wine.getPicture());
      ps.setString(7, wine.getDescription());
      ps.setLong(8, wine.getId());
      ps.executeUpdate();

    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      ConnectionHelper.close(c);
    }
    return wine;
  }

  @Override
  public Wine update(Wine wine) {
    Connection c = null;
    try {
      c = ConnectionHelper.getConnection();
      PreparedStatement ps = c.prepareStatement("UPDATE wine SET name=?, grapes=?, country=?, region=?, year=?, picture=?, description=? WHERE id=?");
      ps.setString(1, wine.getName());
      ps.setString(2, wine.getGrapes());
      ps.setString(3, wine.getCountry());
      ps.setString(4, wine.getRegion());
      ps.setString(5, wine.getYear());
      ps.setString(6, wine.getPicture());
      ps.setString(7, wine.getDescription());
      ps.setLong(8, wine.getId());
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      ConnectionHelper.close(c);
    }
    return wine;
  }

  @Override
  public boolean remove(long id) {
    Connection c = null;
    try {
      c = ConnectionHelper.getConnection();
      PreparedStatement ps = c.prepareStatement("DELETE FROM wine WHERE id=?");
      ps.setLong(1, id);
      int count = ps.executeUpdate();
      return count == 1;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      ConnectionHelper.close(c);
    }
  }

  @Override
  public void clear() {
    throw new RuntimeException("No clean available for mysql");
  }

  @Override
  public void init() {
  }

  protected Wine processRow(ResultSet rs) throws SQLException {
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
