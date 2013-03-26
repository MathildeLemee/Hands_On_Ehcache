package org.coenraets.scripts;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.TerracottaClientConfiguration;
import net.sf.ehcache.config.TerracottaConfiguration;
import org.coenraets.model.Wine;
import org.coenraets.util.WineBuilder;

/**
 * Cette classe ne sert que dans la 2eme partie du hands-on, celle sur le clustering
 *
 * @author Mathilde Lemee
 */
public class WriteToCache {

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
    for (int i = 0; i < 10; i++) {
      Wine wine = WineBuilder.nextWithId();
      manager.getCache("clusteredCache").put(new Element(wine.getId(), wine));
    }
  }


}
