package org.coenraets.service;

import net.sf.ehcache.constructs.blocking.CacheEntryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Mathilde Lemee
 */
@Service
public class MyCacheEntryFactory implements CacheEntryFactory {

  @Autowired
  public WineMysql wineMysql;

  @Override
  public Object createEntry(final Object key) throws Exception {
    System.out.println("ENTRY FACTORY"+key.toString());
    return wineMysql.findById((Long)key);
  }
}
