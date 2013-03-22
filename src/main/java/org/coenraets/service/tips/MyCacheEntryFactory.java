package org.coenraets.service.tips;

import net.sf.ehcache.constructs.blocking.CacheEntryFactory;
import org.coenraets.service.WineMysql;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Aide pour l'exo 2
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
