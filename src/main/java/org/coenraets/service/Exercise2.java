package org.coenraets.service;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.constructs.blocking.CacheEntryFactory;
import org.coenraets.model.Wine;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Cache as a system of record - Read Through
 */
@Service
public class Exercise2 implements WineService {

  @Resource
  WineMysql mysql;

  //TODO : add @Resource annotation on this field and create a class of the type CacheEntryFactory
  CacheEntryFactory myCacheEntryFactory;

  private Ehcache selfPopulatingCache;

  @PostConstruct
  public void postContruct() {
    //TODO
    selfPopulatingCache = null;
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
    throw new RuntimeException("non implemented");
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


}
