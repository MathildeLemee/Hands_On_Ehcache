package org.coenraets.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.constructs.refreshahead.RefreshAheadCache;
import net.sf.ehcache.constructs.refreshahead.RefreshAheadCacheConfiguration;
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

  @Resource
  private CacheLoader cacheLoader;

  private Ehcache wineCache;

  @PostConstruct
  public void postConstruct() {
    CacheManager manager = CacheManager.getInstance();
    if (!manager.cacheExists("refreshAhead")) {
      manager.addCache(new Cache(new CacheConfiguration().name("refreshAhead")
          .maxEntriesLocalHeap(5000).eternal(true)));
    }

    RefreshAheadCacheConfiguration refreshConfig = new RefreshAheadCacheConfiguration().maximumRefreshBacklogItems(100)
        .maximumRefreshBacklogItems(Integer.MAX_VALUE)
        .name("refreshAhead")
        .timeToRefreshSeconds(1)
        .build();

    wineCache = new RefreshAheadCache( manager.getCache("refreshAhead"), refreshConfig);
    wineCache.registerCacheLoader(cacheLoader);
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
    Element element = wineCache.get(id);
    if (element != null) {
      return (Wine)element.getObjectValue();
    }
    return null;
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
