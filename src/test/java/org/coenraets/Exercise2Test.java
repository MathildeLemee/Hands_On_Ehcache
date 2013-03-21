package org.coenraets;

import org.coenraets.service.Exercise2;
import org.coenraets.service.MyCacheEntryFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author : Mathilde Lemee
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class Exercise2Test {
  @Autowired
  private ApplicationContext applicationContext;
  @Resource
  MyCacheEntryFactory myCacheEntryFactory;

  @Resource
  private Exercise2 exercise2;

  @Before
  public void beforeTest() {
  }


  @Test
  public void when_read_through_then_use_selfPopulatingCache() {
    long start = System.currentTimeMillis();
    exercise2.findById(2);
    long delta = (System.currentTimeMillis() - start);
    System.out.println(delta);
     start = System.currentTimeMillis();
    exercise2.findById(2);
     delta = (System.currentTimeMillis() - start);
    System.out.println(delta);
  }



}
