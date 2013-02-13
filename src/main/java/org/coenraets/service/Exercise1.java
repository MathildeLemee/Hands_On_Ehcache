package org.coenraets.service;

import net.sf.ehcache.Cache;
import org.coenraets.model.Wine;

import java.util.List;

/**
 * Des indices pour créer le cache sont dans le fichier tips1.txt dans ce meme répertoire !
 * Saurez vous vous en passer ? :)
 *
 */
public class Exercise1 implements WineService {
  private WineMysql mysql;
  private Cache wineCache;

  public Exercise1() {
      //TODO
  }


  @Override
  public List<Wine> findAll() {
    throw new RuntimeException("not implemented");
  }


  @Override
  public List<Wine> findByName(String name) {
    throw new RuntimeException("not implemented");
  }


  @Override
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
    wineCache.removeAll();
  }

  @Override
  public void init() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  public Cache getCache() {
    return wineCache;
  }

  public void setMysql(final WineMysql mysql) {
    this.mysql = mysql;
  }

  public void setWineCache(final Cache wineCache) {
    this.wineCache = wineCache;
  }
}
