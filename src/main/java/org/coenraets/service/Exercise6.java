package org.coenraets.service;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.coenraets.model.Wine;

import java.util.List;

/**
 * Search exercise
 * <p/>
 * Using the cache as SOR, searching directly into it
 *
 * @author Aurelien Broszniowski
 */
public class Exercise6 implements WineService {

  private Ehcache cache;

  public Exercise6() {
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
    List<Wine> all = new WineMysql().findAll();
    for (Wine wine : all) {
      cache.put(new Element(wine.getId(), wine));
    }
  }

  public Ehcache getCache() {
    return cache;
  }

  public void setCache(final Ehcache cache) {
    this.cache = cache;
  }

}
