package org.coenraets;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.coenraets.model.Wine;
import org.coenraets.service.Exercise1;
import org.coenraets.service.WineMysql;
import org.coenraets.util.WineBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * @author : Mathilde Lemee
 */
public class Exercise1Test {

  @Mock
  WineMysql wineMysql;
  private Exercise1 exercise1;

  @Before
  public void beforeTest() {
    MockitoAnnotations.initMocks(this);
    exercise1 = new Exercise1();
    exercise1.setMysql(wineMysql);
  }

  @Test
  public void when_key_not_in_cache_then_calling_db() throws InterruptedException {

    long id = System.currentTimeMillis();
    exercise1.findById(id);
    verify(wineMysql).findById(id);
  }

  @Test
  public void when_key_in_cache_then_calling_db() {
    long id = System.currentTimeMillis();
    Cache cache = exercise1.getCache();
    Wine wine = WineBuilder.nextWithId();
    cache.put(new Element(id, wine));
    exercise1.findById(id);
    verifyZeroInteractions(wineMysql);
  }
}
