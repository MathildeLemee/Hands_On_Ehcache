package org.coenraets.service;

import net.sf.ehcache.constructs.blocking.CacheEntryFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author : Mathilde Lemee
 */
@Service
public class MyCacheEntryFactory implements CacheEntryFactory {

  @Resource
  public WineMysql wineMysql;

  @Override
  public Object createEntry(final Object key) throws Exception {
    return wineMysql.findById((Long)key);
  }
}
