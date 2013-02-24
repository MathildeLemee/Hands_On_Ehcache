package org.coenraets.resource;

import org.coenraets.model.Wine;
import org.coenraets.service.WineService;
import org.coenraets.util.WineBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Exercice 3 : Write-Through
 * Cache as a sytem-of-record : l'application ne voit plus que le cache, c'est le garant des données.
 * Implémenter l'écriture via la méthode create. La donnée sera écrite dans le cache et c'est le cache qui saura reporter la donnée dans la base de données secondaires, ici wineMysql
 * L'écriture devra etre synchronisée.
 *
 * Indice : Le cacheWriter pourra surement vous aider :)
 *
 *  Rendez vous sur la page exercise3.html pour voir le résultat

 * @author : Mathilde Lemee
 */
@Path("/exercise3")
@Component
public class Exercise3Resource {
  @Resource
  WineService wineMysql;

  @Resource
  WineService exercise3;


  @POST
  @Path("ehcache/create")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public String createEhcache() {
    long start = System.currentTimeMillis();
    exercise3.create(WineBuilder.nextWithId());
    return "" + (System.currentTimeMillis() - start);
  }

  @POST
  @Path("mysql/create")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public String createMysql() {
    long start = System.currentTimeMillis();
     wineMysql.create(WineBuilder.nextWithId());
    return "" + (System.currentTimeMillis() - start);
  }


  @GET
  @Path("ehcache/createAndGet")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public String createAndGetEhcache() {
    Wine next = WineBuilder.nextWithId();
    exercise3.create(next);
    long start = System.currentTimeMillis();
    exercise3.findById(next.getId());
    String s = "" + (System.currentTimeMillis() - start);
    return s;
  }

  @GET
  @Path("mysql/createAndGet")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public String createAndGetMysql() {
    Wine wine = WineBuilder.nextWithId();
    wineMysql.create(wine);
    long start = System.currentTimeMillis();
    wineMysql.findById(wine.getId());
    String s = "" + (System.currentTimeMillis() - start);
    return s;
  }


  @DELETE
  @Path("clear")
  public void clearCache() {
    exercise3.clear();
  }

}
