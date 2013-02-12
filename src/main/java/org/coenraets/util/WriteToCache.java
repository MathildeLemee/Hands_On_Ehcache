package org.coenraets.util;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.TerracottaClientConfiguration;
import net.sf.ehcache.config.TerracottaConfiguration;
import org.coenraets.model.Wine;

/**
 * Cette classe ne sert que dans la 2eme partie du hands-on, celle sur le clustering
 *
 * @author Mathilde Lemee
 */
public class WriteToCache {

  public static void main(String[] args) {
    /** adapt the config to your version
     * - the url of the server
     * - the name of the cache */
    Configuration configuration = new Configuration()
        .terracotta(new TerracottaClientConfiguration().url("localhost:9510"))
        .defaultCache(new CacheConfiguration("defaultCache", 100))
        .cache(new CacheConfiguration("clusteredCache", 100)
            .terracotta(new TerracottaConfiguration()));
    CacheManager manager = new CacheManager(configuration);
    for (int i = 0; i < 10; i++) {
      Wine wine = WineBuilder.nextWithId();
      manager.getCache("clusteredCache").put(new Element(wine.getId(), wine));
    }
  }


}
