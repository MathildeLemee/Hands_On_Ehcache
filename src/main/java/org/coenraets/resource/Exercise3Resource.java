package org.coenraets.resource;

import org.coenraets.model.Wine;
import org.coenraets.service.Exercise3;
import org.coenraets.service.WineMysql;
import org.coenraets.service.WineService;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/exercise3")
public class Exercise3Resource {

  WineService mysql = new WineMysql();
  WineService ehcache = new Exercise3();


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
    System.out.println("EHCACHE SAVE");
    ehcache.create(wine);
    return "" + (System.currentTimeMillis() - start);
  }

  @POST
  @Path("mysql/create")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public String createMysql() {
    long start = System.currentTimeMillis();
    Wine wine = new Wine();
    wine.setId(System.currentTimeMillis()-107);
    wine.setCountry("fr");
    wine.setName("Vin Divers");
    wine.setYear(String.valueOf(System.currentTimeMillis()));
    System.out.println("MYSQL SAVE");
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
    System.out.println("ehcache "+s);
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
    System.out.println("MYSQL :"+s);
    return s;
  }


  @DELETE
  @Path("clear")
  public void clearCache() {
    ehcache.clear();
  }

}
