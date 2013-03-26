package org.coenraets;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.SearchAttribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;
import net.sf.ehcache.search.expression.Criteria;
import org.coenraets.model.Wine;
import org.coenraets.service.Exercise7;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test Ehcache Search
 *
 * @author Aurelien Broszniowski
 */
public class Exercise7Test {

  @Mock Ehcache cache;
  @Mock Query query;
  @Mock Results results;

  @Mock Result result1;
  @Mock Result result2;
  @Mock Result result3;

  @Before
  public void beforeTest() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testCacheIsConfigured() {
    Exercise7 exercise7 = new Exercise7();
    Ehcache cache = exercise7.getCache();
    Assert.assertNotNull("Cache should be created in service", cache);
    Assert.assertTrue("Search attribute on Wine.name field should be set",
        assertThatSearchAttrIsPresent(cache.getCacheConfiguration().getSearchable().getSearchAttributes(), "name"));
  }

  @Test
  public void testFindByNameCreateQuery() {
    Exercise7 exercise7 = new Exercise7();
    exercise7.setCache(cache);

    when(cache.createQuery()).thenReturn(query);
    when(query.addCriteria((Criteria)any())).thenReturn(query);
    when(query.includeValues()).thenReturn(query);
    when(query.includeKeys()).thenReturn(query);

    when(query.execute()).thenReturn(results);

    when(results.all()).thenReturn(new ArrayList<Result>());

    exercise7.findByName("Bordeaux");

    verify(query).addCriteria((Criteria)any());
    verify(cache).createQuery();
  }

  @Test
  public void testFindByNameQueryReturnsCorrectCollection() {
    Exercise7 exercise7 = new Exercise7();
    exercise7.setCache(cache);

    when(cache.createQuery()).thenReturn(query);

    when(query.execute()).thenReturn(results);
    when(query.addCriteria((Criteria)any())).thenReturn(query);
    when(query.includeValues()).thenReturn(query);
    when(query.includeKeys()).thenReturn(query);

    List<Result> resultsList = new ArrayList<Result>();
    resultsList.add(result1);
    resultsList.add(result2);
    resultsList.add(result3);

    when(results.all()).thenReturn(resultsList);
    when(results.size()).thenReturn(3);

    List<Wine> wineList = exercise7.findByName("bordeaux");
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
