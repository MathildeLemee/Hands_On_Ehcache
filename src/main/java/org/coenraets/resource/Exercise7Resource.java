package org.coenraets.resource;

import org.coenraets.model.Wine;
import org.coenraets.service.WineService;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/exercise7")
@Component
public class Exercise7Resource {

  @Resource WineService wineMysql;
  @Resource WineService exercise7;

  @GET
  @Path("initcache")
  public String initCache() {
    exercise7.init();
    return "OK";
  }

  @GET
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public List<Wine> findAll() {
    System.out.println("findAll");
    return wineMysql.findAll();
  }

  @GET
  @Path("search/mysql/{query}")
  public String findByNameMysql(@PathParam("query") String query) {
    long start = System.currentTimeMillis();
    wineMysql.findByName(query);
    long end = System.currentTimeMillis();
    return "" + (end - start);
  }

  @GET
  @Path("search/ehcache/{query}")
  public String findByNameEhcache(@PathParam("query") String query) {
    long start = System.currentTimeMillis();
    exercise7.findByName(query);
    long end = System.currentTimeMillis();
    return "" + (end - start);
  }


  @DELETE
  @Path("clear")
  public void clearCache() {
    exercise7.clear();
  }

}
