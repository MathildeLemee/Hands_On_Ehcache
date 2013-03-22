package org.coenraets;

import net.sf.ehcache.CacheManager;
import org.coenraets.model.Wine;
import org.coenraets.service.Exercise5;
import org.coenraets.util.WineBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Exercice BONUS !
 *
 * @author : Mathilde Lemee
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = { "classpath:applicationContext.xml" })
public class Exercise5Test {
  @Resource
  private Exercise5 exercise5;


  @Test
  public void when_cache_is_stopped_then_data_are_still_there() throws InterruptedException {
    Wine wine = WineBuilder.nextWithId();
    exercise5.create(wine);
    CacheManager.getInstance().shutdown();
    exercise5 = new Exercise5();
    Assert.assertNotNull(exercise5.findById(wine.getId()));

  }


}
