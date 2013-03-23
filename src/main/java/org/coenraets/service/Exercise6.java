package org.coenraets.service;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.SearchAttribute;
import net.sf.ehcache.config.Searchable;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;
import net.sf.ehcache.search.expression.ILike;
import org.coenraets.model.Wine;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Search exercise
 * <p/>
 * Using the cache as SOR, searching directly into it
 *
 * @author Aurelien Broszniowski
 */
@Service
public class Exercise6 implements WineService {

  private Ehcache cache;

  public Exercise6() {
    Searchable searchable = new Searchable();
    searchable.addSearchAttribute(new SearchAttribute().name("name"));

    Configuration configuration = new Configuration().name("searchManager")
        .diskStore(new DiskStoreConfiguration().path("searchManager"))
        .cache(new CacheConfiguration("searchWine", 3)
//            .persistence(new PersistenceConfiguration().strategy(PersistenceConfiguration.Strategy.LOCALRESTARTABLE))
            .searchable(searchable)
        );
    CacheManager manager = CacheManager.newInstance(configuration);
    cache = manager.getCache("searchWine");
  }

  @Override
  public List<Wine> findAll() {
    return null;
  }

  @Override
  public List<Wine> findByName(final String name) {
    Query query = cache.createQuery().addCriteria(new ILike("name", name + "%")).includeValues();
    final Results results = query.execute();
    System.out.println("We found " + results.size() + " results.");

    final List<Result> all = results.all();
    List<Wine> wineList = new ArrayList<Wine>();
    for (Result result : all) {
      wineList.add((Wine)result.getKey());
    }

    return wineList;
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
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void init() {
    List<Wine> all = new WineMysql().findAll();
    for (Wine wine : all) {
      cache.put(new Element(wine.getId(), wine));
    }
  }

  public Ehcache getCache() {
    return cache;
  }

  public void setCache(final Ehcache cache) {
    this.cache = cache;
  }
}
