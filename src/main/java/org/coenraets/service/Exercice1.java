package org.coenraets.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import org.coenraets.model.Wine;

import java.util.List;

/**
 * @author Christophe Coenraets
 */
public class Exercice1 implements WineService {
  WineMysql mysql = new WineMysql();
  private CacheManager manager;
  private Cache searchWine;

  public Exercice1() {
    Configuration configuration = new Configuration()
        .cache(new CacheConfiguration("searchWine", 3));
    this.manager = CacheManager.create(configuration);
    this.searchWine = manager.getCache("searchWine");
  }

  @Override
  public List<Wine> findAll() {
    return mysql.findAll();
  }


  @Override
  public List<Wine> findByName(String name) {
    Element element = searchWine.get(name);
    if (element != null && element.getObjectValue() != null) {
      return (List<Wine>)element.getObjectValue();
    } else {
      List<Wine> list = mysql.findByName(name);
      searchWine.put(new Element(name, list));
      return list;
    }
  }




  @Override
  public Wine findById(long id) {
    return mysql.findById(id);
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
    searchWine.removeAll();
  }

}
