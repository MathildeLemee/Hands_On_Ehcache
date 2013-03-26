package org.coenraets.service.tips;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Status;
import net.sf.ehcache.loader.CacheLoader;
import org.coenraets.service.WineMysql;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

/**
 * Aide pour l'exo 3
 *
 * @author : Mathilde Lemee
 */
@Service
public class MyCacheLoader implements CacheLoader {
  @Resource
  public WineMysql wineMysql;

  @Override
  public Object load(final Object o) throws CacheException {
    return null;
  }

  @Override
  public Map loadAll(final Collection collection) {
    System.out.println("MyCacheLoader -> loadAll");
    Map map = new HashMap();
    for (String idString  : (Collection<String>)collection) {
       map.put(idString,wineMysql.findById(Long.valueOf(idString)));
    }
    return map;
  }

  @Override
  public Object load(final Object o, final Object o2) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Map loadAll(final Collection collection, final Object o) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String getName() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public CacheLoader clone(final Ehcache ehcache) throws CloneNotSupportedException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void init() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void dispose() throws CacheException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Status getStatus() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }
}
