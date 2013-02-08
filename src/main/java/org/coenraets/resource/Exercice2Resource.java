package org.coenraets.resource;

import org.coenraets.service.Exercice2;
import org.coenraets.service.WineMysql;
import org.coenraets.service.WineService;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



/**
 Exercice 2 :  Read-Trough
 Cache sytem-of-record : l'application ne voit plus que le cache, c'est le garant des données.
 Implémenter la lecture via la méthode findBy. Meme lorsque la donnée ne sera pas déja présente dans le cache,
 c'est le cache qui saura comment aller la chercher.
 Indice : SelfPopulatingCache pourra surement vous aider :)

 Rendez vous sur la page Exercice2.html pour voir le résultat

 * @author : Mathilde Lemee
 */
@Path("/exercice2")
public class Exercice2Resource {

  WineService mysql = new WineMysql();
  WineService ehcache = new Exercice2();

  @GET @Path("mysql/{id}")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public String findByIdMysql(@PathParam("id") String id) {
    long start = System.currentTimeMillis();
     mysql.findById(Integer.parseInt(id));
    return ""+(System.currentTimeMillis() - start);
  }

  @GET @Path("ehcache/{id}")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public String findByIdEhcache(@PathParam("id") String id) {
    long start = System.currentTimeMillis();
    ehcache.findById(Integer.parseInt(id));
    return ""+(System.currentTimeMillis() - start);  }


  @DELETE
  @Path("clear")
  public void clearCache() {
    ehcache.clear();
  }

}
