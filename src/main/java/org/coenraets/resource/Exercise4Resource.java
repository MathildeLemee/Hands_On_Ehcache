package org.coenraets.resource;

import org.coenraets.service.WineService;
import org.coenraets.util.WineBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Exercice 4 : Write-Behind
 * Cache sytem-of-record : l'application ne voit plus que le cache, c'est le garant des données.
 * Implémenter l'écriture via la méthode create. La donnée sera écrite dans le cache et c'est le cache qui saura reporter la donnée dans la base de données secondaires, ici wineMysql
 * L'écriture devra etre asynchone.
 * Rendez vous sur la page exercise4.html pour voir le résultat
 *
 * @author : Mathilde Lemee
 */
@Path("/exercise4")
@Component
public class Exercise4Resource {
  @Resource
  WineService exercise3;
  @Resource
  WineService exercise4;

  @POST
  @Path("writeBehind/create")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public String createWriteBehind() {
    long start = System.currentTimeMillis();
    exercise4.create(WineBuilder.nextWithId());
    return "" + (System.currentTimeMillis() - start);
  }

  @POST
  @Path("writeThrough/create")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public String createWriteThrough() {
    long start = System.currentTimeMillis();
    exercise3.create(WineBuilder.nextWithId());
    return "" + (System.currentTimeMillis() - start);
  }


  @DELETE
  @Path("clear")
  public void clearCache() {
    exercise3.clear();
    exercise4.clear();
  }

}
