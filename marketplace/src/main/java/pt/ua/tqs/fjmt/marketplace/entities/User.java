
package pt.ua.tqs.fjmt.marketplace.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class User {

  private @Id @GeneratedValue Long id;
  private String name;
  private String email;
  private Location location;
  private String password;

  User() {}

  User(String name, String email, Location location, String password) {
    this.name = name;
    this.email = email;
    this.location = location;
    this.password = password;
  }
}
