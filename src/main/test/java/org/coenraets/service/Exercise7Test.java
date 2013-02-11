package org.coenraets.service;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.MemoryUnit;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Test Ehcache ARC
 *
 * @author Aurelien Broszniowski
 */
public class Exercise7Test {

  @Test
  public void testCacheIsConfigured() {
    Exercise7 exercise7 = new Exercise7();
    final List<Ehcache> caches = exercise7.getCaches();
    Ehcache cache1 = caches.get(0);
    Ehcache cache2 = caches.get(1);
    Ehcache cache3 = caches.get(2);

    Assert.assertNotNull("Cache should be created in service", cache1);
    Assert.assertNotNull("Cache should be created in service", cache2);
    Assert.assertNotNull("Cache should be created in service", cache3);

    Assert.assertEquals("Cache 1 should have 100m Heap",
        MemoryUnit.MEGABYTES.toBytes(100), cache1.getCacheConfiguration().getMaxBytesLocalHeap() );
    Assert.assertTrue("Cache 1 should overflow to offheap", cache1.getCacheConfiguration().isOverflowToOffHeap());

    Assert.assertEquals("Cache 2 should have 20% Heap",
        MemoryUnit.MEGABYTES.toBytes(80), cache2.getCacheConfiguration().getMaxBytesLocalHeap() );
    Assert.assertTrue("Cache 2 should overflow to offheap", cache2.getCacheConfiguration().isOverflowToOffHeap());

    Assert.assertEquals("Cache 3 should have 200m OffHeap",
        MemoryUnit.MEGABYTES.toBytes(200), cache3.getCacheConfiguration().getMaxBytesLocalOffHeap());

  }


}
