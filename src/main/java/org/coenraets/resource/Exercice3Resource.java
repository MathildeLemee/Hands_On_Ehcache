package org.coenraets.resource;

import org.coenraets.model.Wine;
import org.coenraets.service.Exercice3;
import org.coenraets.service.WineMysql;
import org.coenraets.service.WineService;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Exercice 3 : Write-Through
 * Cache sytem-of-record : l'application ne voit plus que le cache, c'est le garant des données.
 * Implémenter l'écriture via la méthode create. La donnée sera écrite dans le cache et c'est le cache qui saura reporter la donnée dans la base de données secondaires, ici mysql
 * L'écriture devra etre synchronisée.
 *
 * Indice : Le cacheWriter pourra surement vous aider :)
 *
 *  Rendez vous sur la page Exercice3.html pour voir le résultat

 * @author : Mathilde Lemee
 */
@Path("/exercice3")
public class Exercice3Resource {

  WineService mysql = new WineMysql();
  WineService ehcache = new Exercice3();


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

  @POST
  @Path("mysql/create")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public String createMysql() {
    long start = System.currentTimeMillis();
    Wine wine = new Wine();
    wine.setId(System.currentTimeMillis() - 107);
    wine.setCountry("fr");
    wine.setName("Vin Divers");
    wine.setYear(String.valueOf(System.currentTimeMillis()));
    mysql.create(wine);
    return "" + (System.currentTimeMillis() - start);
  }


  @GET
  @Path("ehcache/createAndGet")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public String createAndGetEhcache() {
    Wine wine = new Wine();
    wine.setId(System.currentTimeMillis() - 305);
    wine.setCountry("fr");
    wine.setName("Vin Divers");
    wine.setYear(String.valueOf(System.currentTimeMillis()));
    ehcache.create(wine);
    long start = System.currentTimeMillis();
    ehcache.findById(wine.getId());
    String s = "" + (System.currentTimeMillis() - start);
    return s;
  }

  @GET
  @Path("mysql/createAndGet")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public String createAndGetMysql() {
    Wine wine = new Wine();
    wine.setId(System.currentTimeMillis() - 78);
    wine.setCountry("fr");
    wine.setName("Vin Divers");
    wine.setYear(String.valueOf(System.currentTimeMillis()));
    mysql.create(wine);
    long start = System.currentTimeMillis();
    mysql.findById(wine.getId());
    String s = "" + (System.currentTimeMillis() - start);
    return s;
  }


  @DELETE
  @Path("clear")
  public void clearCache() {
    ehcache.clear();
  }

}
