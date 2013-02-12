package org.coenraets.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Christophe Coenraets
 */
@XmlRootElement
public class Wine implements Serializable {

  private long id;

  private String name;

  private String grapes;

  private String country;

  private String region;

  private String year;

  private String picture;

  private String description;

  public Wine(final String name, final String grapes, final String country, final String region, final String year, final String picture, final String description) {
    this.name = name;
    this.grapes = grapes;
    this.country = country;
    this.region = region;
    this.year = year;
    this.picture = picture;
    this.description = description;
  }

  public Wine() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGrapes() {
    return grapes;
  }

  public void setGrapes(String grapes) {
    this.grapes = grapes;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
