package org.coenraets.service;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.coenraets.model.Wine;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Ehcache ARC and Terracotta Management Console
 * <p/>
 * using several caches
 *
 * @author Aurelien Broszniowski
 */
@Service
public class Exercise9 implements WineService {

  private Ehcache cache1;
  private Ehcache cache2;
  private Ehcache cache3;

  public Exercise9() {
    URL url = getClass().getResource("/ehcache-ex9.xml");
    CacheManager cacheManager = CacheManager.newInstance(url);
    cache1 = cacheManager.getEhcache("wine1");
    cache2 = cacheManager.getEhcache("wine2");
    cache3 = cacheManager.getEhcache("wine3");
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
