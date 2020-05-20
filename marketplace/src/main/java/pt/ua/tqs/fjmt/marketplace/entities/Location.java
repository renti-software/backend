package pt.ua.tqs.fjmt.marketplace.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Location {

  @Id 
  @GeneratedValue 
  private Long id;

  private String cityName;

  private String country;

  public Location() {}

  public Location(String cityName, String country) {
    this.cityName = cityName;
    this.country = country;
  }
}
