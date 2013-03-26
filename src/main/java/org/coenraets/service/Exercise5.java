package org.coenraets.service;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.loader.CacheLoader;
import org.coenraets.model.Wine;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Bonus - Refresh Ahead
 */
@Service
public class Exercise5 implements WineService {
  @Resource
  private WineMysql mysql;

  //TODO : add @Resource annotation on this field and create a class of the type cacheLoader
  private CacheLoader cacheLoader;

  private Ehcache wineCache;

  @PostConstruct
  public void postConstruct() {
    //TODO
    wineCache = null;
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
    throw new RuntimeException("non implemented");

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

  public Ehcache getCache() {
    return wineCache;
  }
}
