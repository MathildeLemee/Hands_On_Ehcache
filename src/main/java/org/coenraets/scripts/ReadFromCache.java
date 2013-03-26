package org.coenraets.scripts;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.TerracottaClientConfiguration;
import net.sf.ehcache.config.TerracottaConfiguration;

import java.util.List;

/**
 * Cette classe ne sert que dans la 2eme partie du hands-on, celle sur le clustering
 *
 * @author Mathilde Lemee
 */
public class ReadFromCache {

  public static void main(String[] args) {
    /**
     * Default config version with only one server
     *
     * adapt the config to your version if multiple server
     * - the url of the server, for a mirror groups, separate active ip and passives ip with a comma
     * - the name of the cache */
    String url = "localhost:9510,localhost:9550";
    Configuration configuration = new Configuration()
        .terracotta(new TerracottaClientConfiguration().url(url))
        .defaultCache(new CacheConfiguration("defaultCache", 100))
        .cache(new CacheConfiguration("clusteredCache", 100)
            .terracotta(new TerracottaConfiguration()));
    CacheManager manager = new CacheManager(configuration);
    Cache clusteredCache = manager.getCache("clusteredCache");
    List keys = clusteredCache.getKeys();
    for (long id : (List<Long>)keys) {
      System.out.println(clusteredCache.get(id));
    }
  }


}
