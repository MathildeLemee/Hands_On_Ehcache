package org.coenraets.service;

import org.coenraets.model.Wine;

import java.util.List;

import javax.annotation.Resource;

/**
 * @author : Mathilde Lemee
 */
public interface WineService {
  List<Wine> findAll();

  List<Wine> findByName(String name);

  Wine findById(long id);

  Wine save(Wine wine);

  Wine create(Wine wine);

  Wine update(Wine wine);

  boolean remove(long id);

  void clear();

  void init();
}
