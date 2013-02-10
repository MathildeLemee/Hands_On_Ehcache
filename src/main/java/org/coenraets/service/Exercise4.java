package org.coenraets.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.coenraets.model.Wine;

import java.util.List;

/**
 * @author Christophe Coenraets
 */
public class Exercise4 implements WineService {
  WineMysql mysql = new WineMysql();
  private CacheManager manager;
  private Cache wineCache;

  public Exercise4() {
    this.manager = CacheManager.getInstance();
    this.wineCache = manager.getCache("writeBehindSOR");
    wineCache.registerCacheWriter(new MyCacheWriter());
  }

  @Override
  public List<Wine> findAll() {
    return mysql.findAll();
  }


  @Override
  public List<Wine> findByName(String name) {
    return mysql.findByName(name);
  }

  @Override
  public Wine findById(long id) {
    return (Wine)wineCache.get(id).getObjectValue();
  }

  @Override
  public Wine save(Wine wine) {
    return mysql.save(wine);
  }

  @Override
  public Wine create(Wine wine) {
    wineCache.putWithWriter(new Element(wine.getId(),wine));
    return null;
  }

  @Override
  public Wine update(Wine wine) {
    return mysql.update(wine);

  }

  @Override
  public boolean remove(long id) {
    return mysql.remove(id);
  }

  @Override
  public void clear() {
    wineCache.removeAll();
  }


}
