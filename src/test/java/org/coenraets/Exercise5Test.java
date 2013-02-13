package org.coenraets;

import net.sf.ehcache.CacheManager;
import org.coenraets.model.Wine;
import org.coenraets.service.Exercise5;
import org.coenraets.util.WineBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author : Mathilde Lemee
 */
public class Exercise5Test {
  private Exercise5 exercise5;

  @Before
  public void beforeTest() {
    exercise5 = new Exercise5();
  }

  @Test
  public void when_cache_is_stopped_then_data_are_still_there() throws InterruptedException {
    Wine wine = WineBuilder.nextWithId();
    exercise5.create(wine);
    CacheManager.getInstance().shutdown();
    exercise5 = new Exercise5();
    Assert.assertNotNull(exercise5.findById(wine.getId()));

  }


}
