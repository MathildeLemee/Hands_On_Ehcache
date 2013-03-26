package org.coenraets.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshCacheExtension;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshConfiguration;
import net.sf.ehcache.writer.CacheWriter;
import org.coenraets.model.Wine;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Cache as a system of record - Write Through
 * Bonus - Schedule Refresh
 */
@Service
public class Exercise6 implements WineService {
  @Resource
  private WineMysql mysql;

  @Resource
  private CacheWriter cacheWriter;

  private Cache wineCache;

  @PostConstruct
  public void postConstruct() {
    CacheManager manager = CacheManager.getInstance();
    if (!manager.cacheExists("scheduleRefresh")) {
      manager.addCache(new Cache(new CacheConfiguration().name("scheduleRefresh")
          .maxEntriesLocalHeap(5000).eternal(true)));
    }
    wineCache = manager.getCache("refreshAhead");
    //premiere mise a jour dans 3 secondes
    int second = (new GregorianCalendar().get(Calendar.SECOND) + 3) % 60;
    ScheduledRefreshConfiguration config = new ScheduledRefreshConfiguration()
        .cronExpression(second + "/1 * * * * ?").build();
    ScheduledRefreshCacheExtension cacheExtension = new ScheduledRefreshCacheExtension(
        config, wineCache);
    cacheExtension.init();
    wineCache.registerCacheExtension(cacheExtension);

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
