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

  @Override
  public String toString() {
    return "Wine{" +
           "id=" + id +
           ", name='" + name + '\'' +
           ", grapes='" + grapes + '\'' +
           ", country='" + country + '\'' +
           ", region='" + region + '\'' +
           ", year='" + year + '\'' +
           ", picture='" + picture + '\'' +
           ", description='" + description + '\'' +
           '}';
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof Wine)) return false;

    final Wine wine = (Wine)o;

    if (id != wine.id) return false;
    if (country != null ? !country.equals(wine.country) : wine.country != null) return false;
    if (description != null ? !description.equals(wine.description) : wine.description != null) return false;
    if (grapes != null ? !grapes.equals(wine.grapes) : wine.grapes != null) return false;
    if (name != null ? !name.equals(wine.name) : wine.name != null) return false;
    if (picture != null ? !picture.equals(wine.picture) : wine.picture != null) return false;
    if (region != null ? !region.equals(wine.region) : wine.region != null) return false;
    if (year != null ? !year.equals(wine.year) : wine.year != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = (int)(id ^ (id >>> 32));
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (grapes != null ? grapes.hashCode() : 0);
    result = 31 * result + (country != null ? country.hashCode() : 0);
    result = 31 * result + (region != null ? region.hashCode() : 0);
    result = 31 * result + (year != null ? year.hashCode() : 0);
    result = 31 * result + (picture != null ? picture.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    return result;
  }
}
