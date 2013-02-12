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
 * Plus aucun appel à la base de données.
 * Toutes les données seront placées dans le cache et celui-ci, meme en cas de crash, gardera en mémoire les données.
 *
 * Indice : Pour stocker les données, il faut les placer sur le disque. Pour ca, il vous faudra activer l'option Fast Restart
 * et définir lors de la configuration du cachemanager le path où sera stocké les données
 *
 Aller ensuite sur la page exercise5.html insere une nouvelle valeur dans le cache par seconde. Elle affiche le nombre d'enregistrements.
 Tuer brutalement jetty (killall java par exemple).
 Relancer l'application et vérifier que le nombre d'élements est resté stable.

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
