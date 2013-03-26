package org.coenraets.service;

import net.sf.ehcache.Cache;
import org.coenraets.model.Wine;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

/**
 * Cache aside
 * Des indices pour créer le cache sont dans le fichier tips1.txt dans ce meme répertoire !
 * Saurez vous vous en passer ? :)
 */
@Service(value = "exercise1")
public class Exercise1 implements WineService {
  @Resource
  private WineMysql mysql;

  private final Cache wineCache;

  public Exercise1() {
    //TODO
    this.wineCache = null;
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

}
