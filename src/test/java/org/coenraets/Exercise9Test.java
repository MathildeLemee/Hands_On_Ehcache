package org.coenraets;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.MemoryUnit;
import org.coenraets.service.Exercise9;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Test Ehcache ARC and Terracotta Management Console
 *
 * @author Aurelien Broszniowski
 *
 *
 * -XX:MaxDirectMemorySize=500m -Xmx600m -Xms600m
 *
 *
 *
 * http://localhost:9889/tmc/
 *
 */
public class Exercise9Test {

  @Test
  public void testCacheIsConfiguredWithTMC() {
    Exercise9 exercise9 = new Exercise9();
    final List<Ehcache> caches = exercise9.getCaches();
    Ehcache cache1 = caches.get(0);
    Ehcache cache2 = caches.get(1);
    Ehcache cache3 = caches.get(2);

    Assert.assertNotNull("Cache should be created in service", cache1);
    Assert.assertNotNull("Cache should be created in service", cache2);
    Assert.assertNotNull("Cache should be created in service", cache3);

    Assert.assertTrue("Cache 1 should overflow to offheap", cache1.getCacheConfiguration().isOverflowToOffHeap());
    Assert.assertTrue("Cache 2 should overflow to offheap", cache2.getCacheConfiguration().isOverflowToOffHeap());
    Assert.assertTrue("Cache 3 should overflow to offheap", cache3.getCacheConfiguration().isOverflowToOffHeap());

    Assert.assertTrue("TMC Monitoring should be enabled",
        cache1.getCacheManager().getConfiguration().getManagementRESTService().isEnabled());
  }

  @Test
  public void testCacheIsConfiguredWithARC() {
    Exercise9 exercise9 = new Exercise9();
    final List<Ehcache> caches = exercise9.getCaches();
    Ehcache cache1 = caches.get(0);
    Ehcache cache2 = caches.get(1);
    Ehcache cache3 = caches.get(2);

    Assert.assertNotNull("Cache should be created in service", cache1);
    Assert.assertNotNull("Cache should be created in service", cache2);
    Assert.assertNotNull("Cache should be created in service", cache3);

    Assert.assertTrue("TMC Monitoring should be enabled",
        cache1.getCacheManager().getConfiguration().getManagementRESTService().isEnabled());

    Assert.assertEquals("CacheManager should have 400m of Heap",
        MemoryUnit.MEGABYTES.toBytes(400), cache1.getCacheManager().getConfiguration().getMaxBytesLocalHeap());
    Assert.assertEquals("CacheManager should have 400m of OffHeap",
        MemoryUnit.MEGABYTES.toBytes(400), cache1.getCacheManager().getConfiguration().getMaxBytesLocalOffHeap());

    Assert.assertEquals("Cache 1 should have 200m Heap",
        MemoryUnit.MEGABYTES.toBytes(200), cache1.getCacheConfiguration().getMaxBytesLocalHeap());
    Assert.assertTrue("Cache 1 should overflow to offheap", cache1.getCacheConfiguration().isOverflowToOffHeap());

    Assert.assertTrue("Cache 2 should overflow to offheap", cache2.getCacheConfiguration().isOverflowToOffHeap());

    Assert.assertEquals("Cache 3 should have 200m OffHeap",
        MemoryUnit.MEGABYTES.toBytes(160), cache3.getCacheConfiguration().getMaxBytesLocalOffHeap());
  }


}
