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


/**
 Exercice 1 :  Cache Aside
 Implémenter un cache aside dans le service exercise1 pour la méthode findBy.
 Un cache aside a l'algo suivant :
 Si j'ai la valeur dans mon cache alors je la retourne
 Sinon je vais la chercher en base de données et je la place dans mon cache

 Une fois que vous etes confiant, rendez-vous sur la page exercise1.html


 * @author : Mathilde Lemee
 */
@Path("/exercise1")
@Component
public class Exercise1Resource {

  @Resource(name="wineMysql")
  WineService wineMysql;

  @Resource(name = "exercise1")
  WineService exercise1;

  @GET
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public List<Wine> findAll() {
    return wineMysql.findAll();
  }

  @GET
  @Path("wines/mysql/{id}")
  public String findByIdMysql(@PathParam("id") Long id) {
    long start = System.currentTimeMillis();
    wineMysql.findById(id);
    return ""+(System.currentTimeMillis() - start);
  }

  @GET
  @Path("wines/ehcache/{id}")
  public String findByIdEhcache(@PathParam("id")Long id) {
    long start = System.currentTimeMillis();
    exercise1.findById(id);
    return ""+(System.currentTimeMillis() - start);
  }


  @DELETE
  @Path("clear")
  public void clearCache() {
    exercise1.clear();
  }


}
