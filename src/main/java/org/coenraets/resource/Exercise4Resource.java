package org.coenraets.resource;

import org.coenraets.model.Wine;
import org.coenraets.service.Exercise3;
import org.coenraets.service.Exercise4;
import org.coenraets.service.WineService;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/exercise4")
public class Exercise4Resource {

  WineService ehcacheWriteThrough = new Exercise3();
  WineService ehcacheWriteBehind= new Exercise4();

  @POST
  @Path("writeBehind/create")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public String createWriteBehind() {
    System.out.println("WRITE BEHIND ");
    Wine wine = new Wine();
    wine.setId(System.currentTimeMillis());
    wine.setCountry("fr");
    wine.setName("Vin Divers");
    wine.setYear(String.valueOf(System.currentTimeMillis()));
    long start = System.currentTimeMillis();
    ehcacheWriteBehind.create(wine);
    return "" + (System.currentTimeMillis() - start);
  }

  @POST
  @Path("writeThrough/create")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public String createWriteThrough() {
    System.out.println("WRITE THROUGH ");
    Wine wine = new Wine();
    wine.setId(System.currentTimeMillis() - 7);
    wine.setCountry("fr");
    wine.setName("Vin Divers");
    wine.setYear(String.valueOf(System.currentTimeMillis()));
    long start = System.currentTimeMillis();
    ehcacheWriteThrough.create(wine);
    return "" + (System.currentTimeMillis() - start);
  }


  @DELETE
  @Path("clear")
  public void clearCache() {
    ehcacheWriteThrough.clear();
    ehcacheWriteBehind.clear();
  }

}
