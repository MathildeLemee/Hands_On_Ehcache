package org.coenraets.service;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.coenraets.model.Wine;

import java.util.Arrays;
import java.util.List;

/**
 * ARC exercise
 * <p/>
 * using several caches
 *
 * @author Aurelien Broszniowski
 */
public class Exercise7 implements WineService {

  private Ehcache cache1;
  private Ehcache cache2;
  private Ehcache cache3;

  public Exercise7() {
    //TODO : implement

  }

  @Override
  public List<Wine> findAll() {
    return null;
  }

  @Override
  public List<Wine> findByName(final String name) {
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
    cache1.removeAll();
    cache2.removeAll();
    cache3.removeAll();
  }

  @Override
  public void init() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  public List<Ehcache> getCaches() {
    return Arrays.asList(cache1, cache2, cache3);
  }

}
