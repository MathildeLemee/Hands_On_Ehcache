package org.coenraets.resource;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.coenraets.service.Exercise7;
import org.coenraets.util.WineBuilder;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/exercise7")
public class Exercise7Resource {

  Exercise7 cacheService = new Exercise7();


  @GET
  @Path("ehcache/fill/{cacheNb}")
  public String fillCaches(@PathParam("cacheNb") final Integer cacheNb) {
    new Thread () {
      @Override
      public void run() {
        System.out.println("--->>> Thread " + Thread.currentThread().getName() + " starting to fill the cache " + cacheNb);
        WineBuilder wineBuilder = new WineBuilder();
        final Ehcache ehcache = cacheService.getCaches().get(cacheNb - 1);
        for (int i = 0; i < 300000; i++) {
          ehcache.put(new Element(UUID.randomUUID().toString(), wineBuilder.next()));
        }
        System.out.println("--->>> Thread " + Thread.currentThread().getName() + " finished.");
      }
    }.start();
    return "OK";
  }

  @GET
  @Path("ehcache/del/{cacheNb}")
  public String emptyCaches(@PathParam("cacheNb") final Integer cacheNb) {
    new Thread () {
      @Override
      public void run() {
        System.out.println("--->>> Thread " + Thread.currentThread().getName() + " starting to empty the cache " + cacheNb);
        final Ehcache ehcache = cacheService.getCaches().get(cacheNb - 1);
        final List keys = ehcache.getKeys();
        for (int i = 0; i < 300000; i++) {
          ehcache.remove(keys.get(i));
        }
        System.out.println("--->>> Thread " + Thread.currentThread().getName() + " finished.");
      }
    }.start();
    return "OK";
  }

  @GET
  @Path("ehcache/sizes")
  public String getCachesSizes(@PathParam("query") String query, @PathParam("cacheNb") Integer cacheNb) {

    StringBuilder jsonString = new StringBuilder();
    jsonString.append("{")
        .append("\"heap1\": ").append(cacheService.getCaches().get(0).calculateInMemorySize()).append(",")
        .append("\"offheap1\": ").append(cacheService.getCaches().get(0).calculateOffHeapSize()).append(",")
        .append("\"heap2\": ").append(cacheService.getCaches().get(1).calculateInMemorySize()).append(",")
        .append("\"offheap2\": ").append(cacheService.getCaches().get(1).calculateOffHeapSize()).append(",")
        .append("\"heap3\": ").append(cacheService.getCaches().get(2).calculateInMemorySize()).append(",")
        .append("\"offheap3\": ").append(cacheService.getCaches().get(2).calculateOffHeapSize())
        .append("}");

    return jsonString.toString();
  }


  @DELETE
  @Path("clear")
  public void clearCache() {
    cacheService.clear();
  }

}
