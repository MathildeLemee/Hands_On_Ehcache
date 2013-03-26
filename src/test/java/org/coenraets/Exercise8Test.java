package org.coenraets;

import net.sf.ehcache.CacheManager;
import org.coenraets.model.Wine;
import org.coenraets.service.Exercise8;
import org.coenraets.util.WineBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Exercice BONUS ! Fast Restartable Store
 *
 * @author : Mathilde Lemee
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = { "classpath:applicationContext.xml" })
public class Exercise8Test {
  @Resource
  private Exercise8 exercise8;


  @Test
  public void when_cache_is_stopped_then_data_are_still_there() throws InterruptedException {
    Wine wine = WineBuilder.nextWithId();
    exercise8.create(wine);
    CacheManager.getInstance().shutdown();
    exercise8 = new Exercise8();
    Assert.assertNotNull(exercise8.findById(wine.getId()));

  }


}
