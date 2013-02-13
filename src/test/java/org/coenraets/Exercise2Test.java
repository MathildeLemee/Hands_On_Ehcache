package org.coenraets;

import net.sf.ehcache.Ehcache;
import org.coenraets.service.Exercise2;
import org.coenraets.service.WineMysql;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * @author : Mathilde Lemee
 */
public class Exercise2Test {

  @Mock
  WineMysql wineMysql;
  @Mock
  Ehcache ehcache;

  private Exercise2 exercise2;

  @Before
  public void beforeTest() {
    MockitoAnnotations.initMocks(this);
    exercise2 = new Exercise2();
    exercise2.setMysql(wineMysql);
    exercise2.setCache(ehcache);
  }

  @Test
  public void when_key_not_in_cache_then_calling_cache() {
    exercise2.findById(1l);
    verifyZeroInteractions(wineMysql);
    verify(ehcache).get(1l);
  }

}
