
package pt.ua.tqs.fjmt.marketplace.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class Location {

  private @Id @GeneratedValue Long id;
  private String city_name;
  private String country;

  Location() {}

  Location(String city_name, String country) {
    this.city_name = city_name;
    this.country = country;
  }
}