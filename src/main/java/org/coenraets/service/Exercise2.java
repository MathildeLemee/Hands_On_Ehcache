package org.coenraets.service;

import net.sf.ehcache.Ehcache;
import org.coenraets.model.Wine;

import java.util.List;

/**
 * @author Christophe Coenraets
 */
public class Exercise2 implements WineService {
  WineMysql mysql;
  private Ehcache selfPopulatingCache;

  public Exercise2() {
    //TODO
  }


  @Override
  public List<Wine> findAll() {
    throw new RuntimeException("not implemented");
  }


  @Override
  public List<Wine> findByName(String name) {
    return mysql.findByName(name);
  }

  @Override
  /**
   * Exercise 2A. Modifier cette méthode. Le système de données est le cache => aucun appel à mysql dans cette méthode.
   * C'est au cache qu'il faut indiquer comment se mettre à jour en cas d'objet non présent dans le cache
   */
  public Wine findById(long id) {
  //TODO
    return null;
  }

  @Override
  public Wine save(Wine wine) {
    throw new RuntimeException("not implemented");
  }

  @Override
  public Wine create(Wine wine) {
    throw new RuntimeException("not implemented");
  }

  @Override
  public Wine update(Wine wine) {
    throw new RuntimeException("not implemented");
  }

  @Override
  public boolean remove(long id) {
    throw new RuntimeException("not implemented");
  }

  @Override
  public void clear() {
    selfPopulatingCache.removeAll();
  }

  @Override
  public void init() {
  }

  public Ehcache getCache() {
    return selfPopulatingCache;
  }

  public WineMysql getMysql() {
    return mysql;
  }

  public void setMysql(final WineMysql mysql) {
    this.mysql = mysql;
  }


  public void setCache(final Ehcache selfPopulatingCache) {
    this.selfPopulatingCache = selfPopulatingCache;
  }
}
