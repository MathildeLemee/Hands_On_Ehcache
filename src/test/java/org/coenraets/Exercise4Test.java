package org.coenraets;

import org.coenraets.model.Wine;
import org.coenraets.service.Exercise4;
import org.coenraets.service.WineMysql;
import org.coenraets.util.WineBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author : Mathilde Lemee
 */
public class Exercise4Test {

  WineMysql wineMysql = new WineMysql();

  private Exercise4 exercise4;

  @Before
  public void beforeTest() {
    exercise4 = new Exercise4();
  }

  @Test
  public void when_wine_is_put_in_the_cache_then_wine_is_not_immediately_in_db() throws InterruptedException {
    Wine next = WineBuilder.nextWithId();
    exercise4.create(next);
    Assert.assertNull(wineMysql.findById(next.getId()));
  }

  @Test
  public void when_wine_is_put_in_the_cache_then_wine_is_in_db_after_n_sec() throws InterruptedException {
    Wine next = WineBuilder.nextWithId();
    exercise4.create(next);
    Thread.sleep(2000); //Si ca ne marche pas, essayer d'augmnter cette valeur arbitraire
    Assert.assertNotNull(wineMysql.findById(next.getId()));
  }

}
