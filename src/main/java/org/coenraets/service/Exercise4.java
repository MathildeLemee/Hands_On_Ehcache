package org.coenraets.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.CacheWriterConfiguration;
import net.sf.ehcache.writer.CacheWriter;
import org.coenraets.model.Wine;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 */
@Service
public class Exercise4 implements WineService {
  @Resource
  private WineMysql mysql;

  @Resource
  private CacheWriter cacheWriter;

  private Cache wineCache;


  @PostConstruct
  public void postConstruct() {
    CacheManager manager = CacheManager.getInstance();
    if (!manager.cacheExists("writeBehind")) {
      CacheConfiguration cacheConfig = new CacheConfiguration("writeBehind", 1000)
          .cacheWriter(new CacheWriterConfiguration().writeMode(CacheWriterConfiguration.WriteMode.WRITE_BEHIND)
          );
      Cache cache = new Cache(cacheConfig);
      manager.addCache(cache);
    }
    this.wineCache = manager.getCache("writeBehind");
    wineCache.registerCacheWriter(cacheWriter);
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
    return (Wine)wineCache.get(id).getObjectValue();
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
