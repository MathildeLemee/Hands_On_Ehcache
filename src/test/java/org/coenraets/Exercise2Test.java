package org.coenraets;

import net.sf.ehcache.constructs.blocking.SelfPopulatingCache;
import org.coenraets.service.Exercise2;
import org.coenraets.service.MyCacheEntryFactory;
import org.coenraets.service.WineMysql;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.kubek2k.springockito.annotations.WrapWithSpy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author : Mathilde Lemee
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = { "classpath:applicationContext.xml" })
public class Exercise2Test extends AbstractJUnit4SpringContextTests {
  @Autowired
  private ApplicationContext applicationContext;

  @Resource
  private Exercise2 exercise2;

  @Resource
  @WrapWithSpy
  private WineMysql wineMysql;


  @Resource
  @WrapWithSpy
  MyCacheEntryFactory myCacheEntryFactory;


  @Before
  public void beforeTest() {
    exercise2.getCache().removeAll();
    reset(myCacheEntryFactory);
    reset(wineMysql);

  }

  @After
  public void afterTest() {

  }

  @Test
  public void when_read_through_then_use_selfPopulatingCache() {
    assertThat(exercise2.getCache()).isInstanceOf(SelfPopulatingCache.class);
  }

  @Test
  public void when_2_read_through_then_only_1_call_to_db() {
    exercise2.findById(2);
    exercise2.findById(2);
    verify(wineMysql, times(1)).findById(2);
  }


  @Test
  public void when_2_read_through_then_cache_entry_factory_is_called_once() throws Exception {
    exercise2.findById(2);
    exercise2.findById(2);
    verify(myCacheEntryFactory, times(1)).createEntry(2l);
  }


  @Test
  public void when_read_through_then_second_access_should_be_faster_than_first() {
    long start = System.currentTimeMillis();
    exercise2.findById(2);
    long delta_first = (System.currentTimeMillis() - start);
    start = System.currentTimeMillis();
    exercise2.findById(2);
    long delta_second = (System.currentTimeMillis() - start);
    assertThat(delta_second).isLessThan(delta_first);
  }
}
