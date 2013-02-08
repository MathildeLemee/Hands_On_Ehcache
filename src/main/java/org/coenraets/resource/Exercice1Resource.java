package org.coenraets.resource;

import org.coenraets.model.Wine;
import org.coenraets.service.WineEhcache;
import org.coenraets.service.WineMysql;
import org.coenraets.service.WineService;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/exercice1")
public class Exercice1Resource {

  WineService mysql = new WineMysql();
  WineService ehcache = new WineEhcache();

  @GET
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public List<Wine> findAll() {
    System.out.println("findAll");
    return mysql.findAll();
  }

  @GET
  @Path("search/mysql/{query}")
  public String findByNameMysql(@PathParam("query") String query) {
    long start = System.currentTimeMillis();
    mysql.findByName("toto");
    return ""+(System.currentTimeMillis() - start);
  }

  @GET
  @Path("search/ehcache/{query}")
  public String findByNameEhcache(@PathParam("query") String query) {
    long start = System.currentTimeMillis();
    ehcache.findByName("toto");
    return ""+(System.currentTimeMillis() - start);
  }

}
