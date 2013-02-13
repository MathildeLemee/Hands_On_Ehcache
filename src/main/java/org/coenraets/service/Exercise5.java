package org.coenraets.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import org.coenraets.model.Wine;

import java.util.List;

/**
 * @author Christophe Coenraets
 */
public class Exercise5 implements WineService {
  private Cache wineCache;

  public Exercise5() {

    CacheManager manager = CacheManager.getInstance();
    if (!manager.cacheExists("frs")) {
      CacheConfiguration cacheConfig = new CacheConfiguration("frs", 1000).persistence(new PersistenceConfiguration().strategy(PersistenceConfiguration.Strategy.LOCALRESTARTABLE));
      Cache cache = new Cache(cacheConfig);
      manager.addCache(cache);
    }
    if (manager.getConfiguration().getDiskStoreConfiguration() == null) {
      manager.getConfiguration().name("frs").diskStore(new DiskStoreConfiguration().path("frs_sav"));
    }
    this.wineCache = manager.getCache("frs");
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
    } else {
      return null;
    }

  }

  @Override
  public Wine save(Wine wine) {
    wineCache.put(new Element(wine.getId(), wine));
    return wine;
  }

  @Override
  public Wine create(Wine wine) {
    wineCache.put(new Element(wine.getId(), wine));
    return wine;
  }

  @Override
  public Wine update(Wine wine) {
    wineCache.put(new Element(wine.getId(), wine));
    return wine;
  }

  @Override
  public boolean remove(long id) {
    return wineCache.remove(id);
  }

  @Override
  public void clear() {
    wineCache.removeAll();
  }

  @Override
  public void init() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  public void setWineCache(final Cache wineCache) {
    this.wineCache = wineCache;
  }
}
