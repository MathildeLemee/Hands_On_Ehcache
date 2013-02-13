package org.coenraets.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.CacheWriterConfiguration;
import org.coenraets.model.Wine;

import java.util.List;

/**
 * @author Christophe Coenraets
 */
public class Exercise3 implements WineService {
  private WineMysql mysql;
  private Cache wineCache;

  public Exercise3() {
    CacheManager manager = CacheManager.getInstance();
    if (!manager.cacheExists("writeThrough")) {
      CacheConfiguration cacheConfig = new CacheConfiguration("writeThrough", 1000)
          .cacheWriter(new CacheWriterConfiguration().writeMode(CacheWriterConfiguration.WriteMode.WRITE_THROUGH)
          );
      Cache cache = new Cache(cacheConfig);
      manager.addCache(cache);
    }
    this.wineCache = manager.getCache("writeThrough");
    wineCache.registerCacheWriter(new MyCacheWriter());
    this.mysql = new WineMysql();
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
    throw new RuntimeException("not implemented");
  }

  @Override
  public Wine save(Wine wine) {
    throw new RuntimeException("not implemented");
  }

  @Override
  public Wine create(Wine wine) {
    wineCache.putWithWriter(new Element(wine.getId(), wine));
    return null;
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
  }

  public void setMysql(final WineMysql mysql) {
    this.mysql = mysql;
  }

  public void setCache(final Cache wineCache) {
    this.wineCache = wineCache;
  }
}
