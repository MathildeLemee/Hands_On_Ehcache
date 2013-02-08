package org.coenraets.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.CacheWriterConfiguration;
import net.sf.ehcache.config.Configuration;
import org.coenraets.model.Wine;

import java.util.List;

/**
 * @author Christophe Coenraets
 */
public class Exercice3 implements WineService {
  WineMysql mysql = new WineMysql();
  private CacheManager manager;
  private Cache wineCache;

  public Exercice3() {
    //TODO : not logic to have to include for exercice4
    Configuration configuration = new Configuration() .
        cache(new CacheConfiguration("writeSOR", 1000)
            .cacheWriter(new CacheWriterConfiguration().writeMode(CacheWriterConfiguration.WriteMode.WRITE_THROUGH)
            )).defaultCache(new CacheConfiguration("default", 1000)).
    cache(new CacheConfiguration("writeBehindSOR", 1000)
        .cacheWriter(new CacheWriterConfiguration().writeMode(CacheWriterConfiguration.WriteMode.WRITE_BEHIND)
        )).defaultCache(new CacheConfiguration("default", 1000));
    this.manager = CacheManager.create(configuration);
    this.wineCache = manager.getCache("writeSOR");
    wineCache.registerCacheWriter(new MyCacheWriter());
  }

  @Override
  public List<Wine> findAll() {
    return mysql.findAll();
  }


  @Override
  public List<Wine> findByName(String name) {
    return mysql.findByName(name);
  }

  @Override
  public Wine findById(long id) {
    return (Wine)wineCache.get(id).getObjectValue();
  }

  @Override
  public Wine save(Wine wine) {
    return mysql.save(wine);
  }

  @Override
  public Wine create(Wine wine) {
     wineCache.putWithWriter(new Element(wine.getId(),wine));
    return null;
  }

  @Override
  public Wine update(Wine wine) {
    return mysql.update(wine);

  }

  @Override
  public boolean remove(long id) {
    return mysql.remove(id);
  }

  @Override
  public void clear() {
    wineCache.removeAll();
  }


}
