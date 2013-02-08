//package org.coenraets.service;
//
//import net.sf.ehcache.CacheManager;
//import net.sf.ehcache.config.CacheConfiguration;
//import net.sf.ehcache.config.Configuration;
//import net.sf.ehcache.constructs.blocking.SelfPopulatingCache;
//
//import org.coenraets.model.Wine;
//import org.coenraets.util.ConnectionHelper;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author Christophe Coenraets
// */
//public class WineEhcacheSOR implements WineService {
//  WineMysql mysql = new WineMysql();
//  private CacheManager manager;
//  private SelfPopulatingCache selfPopulatingCache;
//   private MyCacheEntryFactory myCacheEntryFactory;
//
//  public WineEhcacheSOR() {
//    Configuration configuration = new Configuration()
//        .cache(new CacheConfiguration("searchWine", 3)
//        );
//    this.manager = CacheManager.create(configuration);
//    selfPopulatingCache = new SelfPopulatingCache(manager.getCache("wineIdSor"), new MyCacheEntryFactory());
//  }
//
//  @Override
//  public List<Wine> findAll() {
//    List<Wine> list = new ArrayList<Wine>();
//    Connection c = null;
//    String sql = "SELECT * FROM wine ORDER BY name";
//    try {
//      c = ConnectionHelper.getConnection();
//      Statement s = c.createStatement();
//      ResultSet rs = s.executeQuery(sql);
//      while (rs.next()) {
//        list.add(processRow(rs));
//      }
//    } catch (SQLException e) {
//      e.printStackTrace();
//      throw new RuntimeException(e);
//    } finally {
//      ConnectionHelper.close(c);
//    }
//    return list;
//  }
//
//
//  @Override
//  public List<Wine> findByName(String name) {
//      List<Wine> list = mysql.findByName(name);
//      return list;
//  }
//
//  @Override
//  public Wine findById(int id) {
//    return (Wine) selfPopulatingCache.get(id).getObjectValue();
//  }
//
//  @Override
//  public Wine save(Wine wine) {
//    return wine.getId() > 0 ? update(wine) : create(wine);
//  }
//
//  @Override
//  public Wine create(Wine wine) {
//    Connection c = null;
//    PreparedStatement ps = null;
//    try {
//      c = ConnectionHelper.getConnection();
//      ps = c.prepareStatement("INSERT INTO wine (name, grapes, country, region, year, picture, description) VALUES (?, ?, ?, ?, ?, ?, ?)",
//          new String[] { "ID" });
//      ps.setString(1, wine.getName());
//      ps.setString(2, wine.getGrapes());
//      ps.setString(3, wine.getCountry());
//      ps.setString(4, wine.getRegion());
//      ps.setString(5, wine.getYear());
//      ps.setString(6, wine.getPicture());
//      ps.setString(7, wine.getDescription());
//      ps.executeUpdate();
//      ResultSet rs = ps.getGeneratedKeys();
//      rs.next();
//      // Update the id in the returned object. This is important as this value must be returned to the client.
//      int id = rs.getInt(1);
//      wine.setId(id);
//    } catch (Exception e) {
//      e.printStackTrace();
//      throw new RuntimeException(e);
//    } finally {
//      ConnectionHelper.close(c);
//    }
//    return wine;
//  }
//
//  @Override
//  public Wine update(Wine wine) {
//    Connection c = null;
//    try {
//      c = ConnectionHelper.getConnection();
//      PreparedStatement ps = c.prepareStatement("UPDATE wine SET name=?, grapes=?, country=?, region=?, year=?, picture=?, description=? WHERE id=?");
//      ps.setString(1, wine.getName());
//      ps.setString(2, wine.getGrapes());
//      ps.setString(3, wine.getCountry());
//      ps.setString(4, wine.getRegion());
//      ps.setString(5, wine.getYear());
//      ps.setString(6, wine.getPicture());
//      ps.setString(7, wine.getDescription());
//      ps.setInt(8, wine.getId());
//      ps.executeUpdate();
//    } catch (SQLException e) {
//      e.printStackTrace();
//      throw new RuntimeException(e);
//    } finally {
//      ConnectionHelper.close(c);
//    }
//    return wine;
//  }
//
//  @Override
//  public boolean remove(int id) {
//    Connection c = null;
//    try {
//      c = ConnectionHelper.getConnection();
//      PreparedStatement ps = c.prepareStatement("DELETE FROM wine WHERE id=?");
//      ps.setInt(1, id);
//      int count = ps.executeUpdate();
//      return count == 1;
//    } catch (Exception e) {
//      e.printStackTrace();
//      throw new RuntimeException(e);
//    } finally {
//      ConnectionHelper.close(c);
//    }
//  }
//
//  protected Wine processRow(ResultSet rs) throws SQLException {
//    Wine wine = new Wine();
//    wine.setId(rs.getInt("id"));
//    wine.setName(rs.getString("name"));
//    wine.setGrapes(rs.getString("grapes"));
//    wine.setCountry(rs.getString("country"));
//    wine.setRegion(rs.getString("region"));
//    wine.setYear(rs.getString("year"));
//    wine.setPicture(rs.getString("picture"));
//    wine.setDescription(rs.getString("description"));
//    return wine;
//  }
//
//}
