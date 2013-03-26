package org.coenraets.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.coenraets.model.Wine;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 */
@Service
public class Exercise8 implements WineService {
  private Cache wineCache;

  public Exercise8() {
    this.wineCache = null;
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
