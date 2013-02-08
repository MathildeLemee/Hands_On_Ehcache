package org.coenraets.service;

import net.sf.ehcache.CacheEntry;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.writer.CacheWriter;
import net.sf.ehcache.writer.writebehind.operations.SingleOperationType;

import org.coenraets.model.Wine;

import java.util.Collection;

/**
 * @author : Mathilde Lemee
 */
public class MyCacheWriter implements CacheWriter {
  public WineMysql wineMysql;

  @Override
  public CacheWriter clone(final Ehcache ehcache) throws CloneNotSupportedException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void init() {
  }

  @Override
  public void dispose() throws CacheException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void write(final Element element) throws CacheException {
    wineMysql.save((Wine)element.getObjectValue());
  }

  @Override
  public void writeAll(final Collection<Element> elements) throws CacheException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void delete(final CacheEntry cacheEntry) throws CacheException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void deleteAll(final Collection<CacheEntry> cacheEntries) throws CacheException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void throwAway(final Element element, final SingleOperationType singleOperationType, final RuntimeException e) {
    //To change body of implemented methods use File | Settings | File Templates.
  }
}
