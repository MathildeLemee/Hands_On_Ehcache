package org.coenraets.service;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.Searchable;
import org.coenraets.model.Wine;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

/**
 * Search exercise
 * <p/>
 * Using the cache as SOR, searching directly into it
 *
 * @author Aurelien Broszniowski
 */
@Service
public class Exercise7 implements WineService {

  private Ehcache cache;

  @Resource
  private WineMysql mysql;

  public Exercise7() {
    Searchable searchable = new Searchable();
     //TODO : implement
  }

  @Override
  public List<Wine> findAll() {
    return null;
  }

  @Override
  public List<Wine> findByName(final String name) {
   // TODO : implement

    return null;
  }

  @Override
  public Wine findById(final long id) {
    return null;
  }

  @Override
  public Wine save(final Wine wine) {
    return null;
  }

  @Override
  public Wine create(final Wine wine) {
    return null;
  }

  @Override
  public Wine update(final Wine wine) {
    return null;
  }

  @Override
  public boolean remove(final long id) {
    return false;
  }

  @Override
  public void clear() {
    cache.removeAll();
  }

  @Override
  public void init() {
    List<Wine> all = mysql.findAll();
    for (Wine wine : all) {
      cache.put(new Element(wine.getId(), wine));
    }
    System.out.println("-------->>>>cache.getSize() : " + cache.getSize());
  }

  public Ehcache getCache() {
    return cache;
  }

  public void setCache(final Ehcache cache) {
    this.cache = cache;
  }
}
