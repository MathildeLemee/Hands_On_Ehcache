package org.coenraets;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.SearchAttribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;
import net.sf.ehcache.search.expression.Criteria;
import org.coenraets.model.Wine;
import org.coenraets.service.Exercise6;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test Ehcache Search
 *
 * @author Aurelien Broszniowski
 */
public class Exercise6Test {

  @Test
  public void testCacheIsConfigured() {
    Exercise6 exercise6 = new Exercise6();
    Ehcache cache = exercise6.getCache();
    Assert.assertNotNull("Cache should be created in service", cache);
    Assert.assertTrue("Search attribute on Wine.name field should be set",
        assertThatSearchAttrIsPresent(cache.getCacheConfiguration().getSearchable().getSearchAttributes(), "name"));
/*
    Assert.assertEquals("Cache should be restartable",
        PersistenceConfiguration.Strategy.LOCALRESTARTABLE,
        cache.getCacheConfiguration().getPersistenceConfiguration().getStrategy());
*/
  }

  @Test
  public void testFindByNameCreateQuery() {
    Ehcache cache = mock(Ehcache.class);
    Exercise6 exercise6 = new Exercise6();
    exercise6.setCache(cache);

    Query query = mock(Query.class);
    when(cache.createQuery()).thenReturn(query);
    when(query.addCriteria((Criteria)any())).thenReturn(query);

    Results results = mock(Results.class);
    when(query.execute()).thenReturn(results);

    when(results.all()).thenReturn(new ArrayList<Result>());

    exercise6.findByName("Bordeaux");

    verify(query).addCriteria((Criteria)any());
    verify(cache).createQuery();
  }

  @Test
  public void testFindByNameQueryReturnsCorrectCollection() {
    Ehcache cache = mock(Ehcache.class);
    Exercise6 exercise6 = new Exercise6();
    exercise6.setCache(cache);

    Query query = mock(Query.class);
    when(cache.createQuery()).thenReturn(query);

    Results results = mock(Results.class);
    when(query.execute()).thenReturn(results);
    when(query.addCriteria((Criteria)any())).thenReturn(query);

    Result result1 = mock(Result.class);
    Result result2 = mock(Result.class);
    Result result3 = mock(Result.class);
    List<Result> resultsList = new ArrayList<Result>();
    resultsList.add(result1);
    resultsList.add(result2);
    resultsList.add(result3);

    when(results.all()).thenReturn(resultsList);
    when(results.size()).thenReturn(3);

    List<Wine> wineList = exercise6.findByName("Beaujolais");
    Assert.assertEquals(resultsList.size(), wineList.size());
    Assert.assertEquals(3, wineList.size());

    verify(query).execute();
  }

  private boolean assertThatSearchAttrIsPresent(Map<String, SearchAttribute> searchAttributes, String name) {
    boolean result = false;
    for (String key : searchAttributes.keySet()) {
      final SearchAttribute searchAttribute = searchAttributes.get(key);
      if (name.equals(searchAttribute.getName())) {
        return true;
      }
    }
    return result;
  }

}
