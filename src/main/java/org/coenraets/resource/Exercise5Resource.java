package org.coenraets.resource;

import net.sf.ehcache.CacheManager;
import org.coenraets.service.Exercise5;
import org.coenraets.service.WineService;
import org.coenraets.util.WineBuilder;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Exercice 5 : Cache ou datastore ?
 * Faire en sorte que la base de données ne soit plus utile.
 * Toutes les données seront placées dans le cache et celui-ci, meme en cas de crash, gardera en mémoire les données.
 *
 *
 * @author : Mathilde Lemee
 */
@Path("/exercise5")
public class Exercise5Resource {

  WineService ehcache = new Exercise5();

  @POST
  @Path("ehcache/create")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public String createEhcache() {
    long start = System.currentTimeMillis();
     ehcache.create(WineBuilder.nextWithId());
    return "" + (System.currentTimeMillis() - start);
  }

  @GET
  @Path("ehcache/nb")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public String nbElement() {
    return "" + ( CacheManager.getInstance().getCache("frs").getSize());
  }

  @DELETE
  @Path("clear")
  public void clearCache() {
    ehcache.clear();
  }

}
