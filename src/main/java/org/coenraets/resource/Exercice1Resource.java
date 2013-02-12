package org.coenraets.resource;

import org.coenraets.model.Wine;
import org.coenraets.service.Exercice1;
import org.coenraets.service.WineMysql;
import org.coenraets.service.WineService;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 Exercice 1 :  Cache Aside
 Implémenter un cache aside dans le service exercice1 pour la méthode findBy.
 Un cache aside a l'algo suivant :
 Si j'ai la valeur dans mon cache alors je la retourne
 Sinon je vais la chercher en base de données et je la place dans mon cache

 Une fois que vous etes confiant, rendez-vous sur la page Exercice1.html


 * @author : Mathilde Lemee
 */
@Path("/exercice1")
public class Exercice1Resource {

  WineService mysql = new WineMysql();
  WineService ehcache = new Exercice1();

  @GET
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public List<Wine> findAll() {
    return mysql.findAll();
  }

  @GET
  @Path("wines/mysql/{id}")
  public String findByIdMysql(@PathParam("id") Long id) {
    long start = System.currentTimeMillis();
    mysql.findById(id);
    return ""+(System.currentTimeMillis() - start);
  }

  @GET
  @Path("wines/ehcache/{id}")
  public String findByIdEhcache(@PathParam("id")Long id) {
    long start = System.currentTimeMillis();
    ehcache.findById(id);
    return ""+(System.currentTimeMillis() - start);
  }


  @DELETE
  @Path("clear")
  public void clearCache() {
    ehcache.clear();
  }


}
