package org.coenraets.service;

import net.sf.ehcache.constructs.blocking.CacheEntryFactory;
import org.coenraets.service.WineMysql;

/**
 * @author : Mathilde Lemee
 */
public class MyCacheEntryFactory implements CacheEntryFactory {
  public WineMysql wineMysql = new WineMysql();


  @Override
  public Object createEntry(final Object key) throws Exception {
    return wineMysql.findById((Integer)key);
  }
}
