package org.coenraets.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import org.coenraets.model.Wine;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

/**
 * Des indices pour créer le cache sont dans le fichier tips1.txt dans ce meme répertoire !
 * Saurez vous vous en passer ? :)
 *
 */
@Service(value = "exercise1")
public class Exercise1 implements WineService {
  @Resource
  private WineMysql mysql;
  private final Cache wineCache;

  public Exercise1() {
      CacheManager manager = CacheManager.getInstance();
      if (!manager.cacheExists("wineCache")) {
        CacheConfiguration cacheConfig = new CacheConfiguration("wineCache", 1000);
        Cache cache = new Cache(cacheConfig);
        manager.addCache(cache);
      }
      this.wineCache = manager.getCache("wineCache");
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
      if (element != null && element.getObjectValue() != null) {
        return (Wine)element.getObjectValue();
      } else {
        Wine wine = mysql.findById(id);
        wineCache.put(new Element(id, wine));
        return wine;
      }
    }

    @Override
    public Wine save(Wine wine) {
      throw new RuntimeException("not implemented");
    }

    @Override
    public Wine create(Wine wine) {
      throw new RuntimeException("not implemented");
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
      //To change body of implemented methods use File | Settings | File Templates.
    }

    public Cache getCache() {
      return wineCache;
    }

    public void setMysql(final WineMysql mysql) {
      this.mysql = mysql;
    }

  }
