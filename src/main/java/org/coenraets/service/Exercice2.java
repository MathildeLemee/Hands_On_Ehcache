package org.coenraets.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.constructs.blocking.SelfPopulatingCache;
import org.coenraets.model.Wine;

import java.util.List;

/**
 * @author Christophe Coenraets
 */
public class Exercice2 implements WineService {
  WineMysql mysql = new WineMysql();
  private CacheManager manager;
  private Cache wine;
  private SelfPopulatingCache selfPopulatingCache;

  public Exercice2() {
    Configuration configuration = new Configuration()
        .cache(new CacheConfiguration("writeSOR", 1000));
    this.manager = CacheManager.create(configuration);
    this.wine = manager.getCache("writeSOR");
    MyCacheEntryFactory myCacheEntryFactory = new MyCacheEntryFactory();
    selfPopulatingCache = new SelfPopulatingCache(wine, myCacheEntryFactory);
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
  /**
   * Exercice 2A. Modifier cette méthode. Le système de données est le cache => aucun appel à mysql dans cette méthode.
   * C'est au cache qu'il faut indiquer comment se mettre à jour en cas d'objet non présent dans le cache
   */
  public Wine findById(long id) {
       return (Wine)selfPopulatingCache.get(id).getObjectValue();
  }

  @Override
  public Wine save(Wine wine) {
    return mysql.save(wine);
  }

  @Override
  public Wine create(Wine wine) {
    return mysql.create(wine);
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
    selfPopulatingCache.removeAll();
  }


}
