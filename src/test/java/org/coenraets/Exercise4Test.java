package org.coenraets;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.CacheWriterConfiguration;
import org.coenraets.model.Wine;
import org.coenraets.service.Exercise4;
import org.coenraets.service.MyCacheWriter;
import org.coenraets.service.WineMysql;
import org.coenraets.util.WineBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.kubek2k.springockito.annotations.WrapWithSpy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;

/**
 * @author : Mathilde Lemee
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = { "classpath:applicationContext.xml" })
public class Exercise4Test {

  @Autowired
  private ApplicationContext applicationContext;

  @Resource
  private Exercise4 exercise;

  @Resource
  @WrapWithSpy
  private WineMysql wineMysql;


  @Resource
  @WrapWithSpy
  MyCacheWriter myCacheWriter;


  @Before
  public void beforeTest() {
    exercise.getCache().removeAll();
    reset(myCacheWriter);
    reset(wineMysql);

  }


  @Test
  public void when_write_behind_then_use_CacheWriterConfiguration_with_mode_WRITE_BEHIND() {
    Ehcache cache = exercise.getCache();
    CacheConfiguration cacheConfiguration = cache.getCacheConfiguration();
    CacheWriterConfiguration cacheWriterConfiguration = cacheConfiguration.getCacheWriterConfiguration();
    assertThat(cacheWriterConfiguration.getWriteMode()).isEqualTo(CacheWriterConfiguration.WriteMode.WRITE_BEHIND);
  }



  @Test
  public void after_write_behind_then_data_is_in_cache() {
    Wine wine = WineBuilder.nextWithId();
    exercise.create(wine);
    assertThat(exercise.findById(wine.getId())).isEqualTo(wine);
  }

  @Test
  public void after_write_behind_then_data_is_not_in_db() {
    Wine wine = WineBuilder.nextWithId();
    exercise.create(wine);
    assertThat(wineMysql.findById(wine.getId())).isNull();
  }

  @Test
  public void after_write_behind_then_data_is_in_db_after_3sec() throws InterruptedException {
    Wine wine = WineBuilder.nextWithId();
    exercise.create(wine);
    Thread.sleep(3000);      //this value is arbitrary, you can augment it if you think that the test fail with no reason
    assertThat(wineMysql.findById(wine.getId())).isEqualTo(wine);
  }

}
