package org.coenraets;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.CacheWriterConfiguration;
import net.sf.ehcache.writer.CacheWriter;
import org.coenraets.model.Wine;
import org.coenraets.service.Exercise3;
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
import static org.mockito.Mockito.verify;

/**
 * @author : Mathilde Lemee
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = { "classpath:applicationContext.xml" })
public class Exercise3Test {

  @Autowired
  private ApplicationContext applicationContext;

  @Resource
  private Exercise3 exercise3;

  @Resource
  @WrapWithSpy
  private WineMysql wineMysql;


  //specifier son nom en utilisant @Resource(name="toto")
  @Resource
  @WrapWithSpy
  CacheWriter myCacheWriter;


  @Before
  public void beforeTest() {
    exercise3.getCache().removeAll();
    reset(myCacheWriter);
    reset(wineMysql);

  }


  @Test
  public void when_write_through_then_use_CacheWriterConfiguration_with_mode_WRITE_THROUGH() {
    Ehcache cache = exercise3.getCache();
    CacheConfiguration cacheConfiguration = cache.getCacheConfiguration();
    CacheWriterConfiguration cacheWriterConfiguration = cacheConfiguration.getCacheWriterConfiguration();
    assertThat(cacheWriterConfiguration.getWriteMode()).isEqualTo(CacheWriterConfiguration.WriteMode.WRITE_THROUGH);
  }


  @Test
  //Aide à la fin de cette classe
  public void when_write_through_then_user_have_modified_the_create_method() {
    Wine wine = WineBuilder.nextWithId();
    exercise3.create(wine);
    verify(wineMysql).create(wine);
  }

  @Test
  public void after_write_through_then_data_is_in_cache() {
    Wine wine = WineBuilder.nextWithId();
    exercise3.create(wine);
    assertThat(exercise3.findById(wine.getId())).isEqualTo(wine);
  }

  @Test
  public void after_write_behind_then_data_is_in_db() {
    Wine wine = WineBuilder.nextWithId();
    exercise3.create(wine);
    assertThat(wineMysql.findById(wine.getId())).isNotNull();
  }
  /**
   * TIPS :
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   *
   * s
   */
  @Test
  public void when_write_through_then_put_with_writer_writes_the_value_in_db() {
    Wine wine = WineBuilder.nextWithId();
    Element element = new Element(wine.getId(), wine);
    exercise3.getCache().putWithWriter(element);
    verify(wineMysql).create(wine);
  }



}
