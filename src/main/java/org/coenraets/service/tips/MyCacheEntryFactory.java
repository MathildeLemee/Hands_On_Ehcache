package org.coenraets.service.tips;

import net.sf.ehcache.constructs.blocking.CacheEntryFactory;
import org.coenraets.service.WineMysql;

import javax.annotation.Resource;

/**
 * Aide pour l'exo 2
 * @author : Mathilde Lemee
 */
//add @Service to make it work
public class MyCacheEntryFactory implements CacheEntryFactory {

  @Resource
  public WineMysql wineMysql;

  @Override
  public Object createEntry(final Object key) throws Exception {
    return wineMysql.findById((Long)key);
  }
}
