package org.coenraets.resource;

import org.coenraets.model.Wine;
import org.coenraets.service.Exercise6;
import org.coenraets.service.WineMysql;
import org.coenraets.service.WineService;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/exercise6")
public class Exercise6Resource {

  WineService sqlService = new WineMysql();
  WineService cacheService = new Exercise6();

  @GET
  @Path("initcache")
  public String initCache() {
    cacheService.init();
    return "OK";
  }

  @GET
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public List<Wine> findAll() {
    System.out.println("findAll");
    return sqlService.findAll();
  }

  @GET
  @Path("search/mysql/{query}")
  public String findByNameMysql(@PathParam("query") String query) {
    long start = System.currentTimeMillis();
    sqlService.findByName(query);
    long end = System.currentTimeMillis();
    return "" + (end - start);
  }

  @GET
  @Path("search/ehcache/{query}")
  public String findByNameEhcache(@PathParam("query") String query) {
    long start = System.currentTimeMillis();
    System.out.println(")) "+ cacheService.findByName(query));;
    long end = System.currentTimeMillis();
    return "" + (end - start);
  }


  @DELETE
  @Path("clear")
  public void clearCache() {
    cacheService.clear();
  }

}
