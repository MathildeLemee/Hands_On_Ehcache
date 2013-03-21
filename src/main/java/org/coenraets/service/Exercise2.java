package org.coenraets.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.constructs.blocking.SelfPopulatingCache;
import org.coenraets.model.Wine;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

/**
 */
@Service
public class Exercise2 implements WineService {
  @Resource
  WineMysql mysql;

  private Ehcache selfPopulatingCache;

  public Exercise2() {
    CacheManager manager = CacheManager.getInstance();
    if (!manager.cacheExists("readThrough")) {
      CacheConfiguration cacheConfig =  new CacheConfiguration("readThrough", 1000);
      Cache cache = new Cache(cacheConfig);
      manager.addCache(cache);
    }
    Cache wine = manager.getCache("readThrough");
    mysql = new WineMysql();
    MyCacheEntryFactory myCacheEntryFactory = new MyCacheEntryFactory();
    selfPopulatingCache = new SelfPopulatingCache(wine, myCacheEntryFactory);
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
    Element element = selfPopulatingCache.get(id);
    if (element == null) {
      return null;
    }
    return (Wine)element.getObjectValue();
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
