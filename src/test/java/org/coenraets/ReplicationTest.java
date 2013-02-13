package org.coenraets;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.junit.Test;

/**
 * @author : Mathilde Lemee
 */
public class ReplicationTest {

  private CacheManager manager1;

  @Test
  public void when_wan_replication_then_see_bridge_access_on_the_log() {
    manager1 = new CacheManager("src/main/resources/ehcache-wan-replication.xml");
    manager1.setName("manager1");
    Cache sampleCacheSync = manager1.getCache("sampleCache");
    sampleCacheSync.put(new Element("1", "2"));
  }

  @Test
  public void when_simple_replication() {
    manager1 = new CacheManager("src/main/resources/ehcache-replication.xml");
    manager1.setName("manager1");
    Cache sampleCacheSync = manager1.getCache("sampleCache");
    sampleCacheSync.put(new Element("1", "2"));
  }

}
