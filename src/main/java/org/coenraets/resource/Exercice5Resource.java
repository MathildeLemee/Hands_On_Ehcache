package org.coenraets.resource;

import net.sf.ehcache.CacheManager;
import org.coenraets.model.Wine;
import org.coenraets.service.Exercice5;
import org.coenraets.service.WineService;
import javax.ws.rs.GET;

import javax.ws.rs.DELETE;
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
@Path("/exercice5")
public class Exercice5Resource {

  WineService ehcache = new Exercice5();

  @POST
  @Path("ehcache/create")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public String createEhcache() {
    long start = System.currentTimeMillis();
    Wine wine = new Wine();
    wine.setId(System.currentTimeMillis());
    wine.setCountry("fr");
    wine.setName("Vin Divers");
    wine.setYear(String.valueOf(System.currentTimeMillis()));
    ehcache.create(wine);
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
