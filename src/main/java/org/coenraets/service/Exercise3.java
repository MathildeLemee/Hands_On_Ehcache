package org.coenraets.service;

import net.sf.ehcache.Cache;
import org.coenraets.model.Wine;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

/**
 */
@Service
public class Exercise3 implements WineService {
  @Resource
  private WineMysql mysql;

  private Cache wineCache;

  public Exercise3() {
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
    //TODO
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
