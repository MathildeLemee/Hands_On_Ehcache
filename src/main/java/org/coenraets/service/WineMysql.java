package org.coenraets.service;

import org.coenraets.model.Wine;
import org.h2.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

/**
 * @author Christophe Coenraets
 */
@Service
public class WineMysql implements WineService {

  public DataSource dataSource = new SimpleDriverDataSource(new Driver(),"jdbc:h2:tcp://localhost:8092/data;DB_CLOSE_DELAY=-1;MVCC=TRUE","sa","");

  @Override
  public List<Wine> findAll() {
    List<Wine> list = new ArrayList<Wine>();
    Connection c = null;
    String sql = "SELECT * FROM public.wine";
    try {
      c = dataSource.getConnection();
      Statement s = c.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        list.add(processRow(rs));
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      try {
        if (c != null) {
          c.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return list;
  }


  @Override
  public List<Wine> findByName(String name) {
    List<Wine> list = new ArrayList<Wine>();
    Connection c = null;
    String sql = "SELECT * FROM  public.wine as e WHERE LOWER(name) = ? ";
    try {
      c = dataSource.getConnection();
      PreparedStatement ps = c.prepareStatement(sql);
      ps.setString(1, name.toLowerCase());
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        list.add(processRow(rs));
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      try {
        if (c != null) {
          c.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    System.out.println("Mysql 'findByName' query found " + list.size() + " results.");
    return list;
  }

  @Override
  public Wine findById(long id) {
    long start = System.currentTimeMillis();
    int nbResults = 0;

    Random rnd = new Random();
    Wine wine = null;
    Connection c = null;
    try {
      c = dataSource.getConnection();

      StringBuilder sql = new StringBuilder("select * from PUBLIC.WINE WHERE id = ?");
      for (int i = 0; i < 5; i++) {
        sql.append(" OR id = ?");
      }

      PreparedStatement ps = c.prepareStatement(sql.toString());
      ps.setLong(1, id);
      for (int i = 0; i < 5; i++) {
        ps.setLong(2 + i, (long)(rnd.nextDouble() * id));
      }

      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        wine = processRow(rs);
        nbResults++;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      try {
        if (c != null) {
          c.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    long end = System.currentTimeMillis();
    System.out.println("----> For id = " + id + " - Nb results " + nbResults + ", time taken =" + (end - start) + "ms");
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
      c = dataSource.getConnection();
      ps = c.prepareStatement("INSERT INTO  public.wine (name, grapes, country, region, year, picture, description,id) VALUES (?, ?, ?, ?, ?, ?, ?,?)");
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
      try {
        if (c != null) {
          c.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return wine;
  }

  @Override
  public Wine update(Wine wine) {
    Connection c = null;
    try {
      c = dataSource.getConnection();
      PreparedStatement ps = c.prepareStatement("UPDATE  public.wine SET name=?, grapes=?, country=?, region=?, year=?, picture=?, description=? WHERE id=?");
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
      try {
        if (c != null) {
          c.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return wine;
  }

  @Override
  public boolean remove(long id) {
    Connection c = null;
    try {
      c = dataSource.getConnection();
      PreparedStatement ps = c.prepareStatement("DELETE FROM  public.wine WHERE id=?");
      ps.setLong(1, id);
      int count = ps.executeUpdate();
      return count == 1;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      try {
        if (c != null) {
          c.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
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
