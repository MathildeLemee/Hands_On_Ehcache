package org.coenraets;

import org.coenraets.model.Wine;
import org.coenraets.service.Exercise3;
import org.coenraets.service.WineMysql;
import org.coenraets.util.WineBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * @author : Mathilde Lemee
 */
public class Exercise3Test {

  WineMysql wineMysql = new WineMysql();

  private Exercise3 exercise3;

  @Before
  public void beforeTest() {
    exercise3 = new Exercise3();
  }

  @Test
  public void when_wine_is_put_in_the_cache_then_wine_is_in_db() {
    Wine next = WineBuilder.nextWithId();
    exercise3.create(next);
    Assert.assertNotNull(wineMysql.findById(next.getId()));
  }

  @Test
  public void when_wine_is_put_in_the_cache_then_no_direct_call_to_db() {
    WineMysql wineMysqlMock = mock(WineMysql.class);
    exercise3.setMysql(wineMysqlMock);
    verifyZeroInteractions(wineMysqlMock);
  }

}
