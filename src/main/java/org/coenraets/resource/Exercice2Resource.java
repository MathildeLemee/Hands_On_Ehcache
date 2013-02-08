package org.coenraets.resource;

import org.coenraets.model.Wine;
import org.coenraets.service.Exercice2;
import org.coenraets.service.WineMysql;
import org.coenraets.service.WineService;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/exercice2")
public class Exercice2Resource {

  WineService mysql = new WineMysql();
  WineService ehcache = new Exercice2();

  @GET
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public List<Wine> findAll() {
    System.out.println("findAll");
    return mysql.findAll();
  }

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
