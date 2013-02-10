package org.coenraets.resource;

import org.coenraets.model.Wine;
import org.coenraets.service.Exercise1;
import org.coenraets.service.WineMysql;
import org.coenraets.service.WineService;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/exercise1")
public class Exercise1Resource {

  WineService sqlService = new WineMysql();
  WineService cacheService = new Exercise1();

  @GET
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public List<Wine> findAll() {
    System.out.println("findAll");
    return sqlService.findAll();
  }

  @GET
  @Path("search/mysql/{query}")
  /**
   * TODO :
   */
  public String findByNameMysql(@PathParam("query") String query) {
    long start = System.currentTimeMillis();
    sqlService.findByName(query);
    long end = System.currentTimeMillis();
    return ""+(end - start);
  }

  @GET
  @Path("search/ehcache/{query}")
  public String findByNameEhcache(@PathParam("query") String query) {
    long start = System.currentTimeMillis();
    cacheService.findByName(query);
    long end = System.currentTimeMillis();
    return ""+(end - start);
  }


  @DELETE
  @Path("clear")
  public void clearCache() {
    cacheService.clear();
  }


}
