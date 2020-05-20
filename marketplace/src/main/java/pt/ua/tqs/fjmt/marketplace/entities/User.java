
package pt.ua.tqs.fjmt.marketplace.entities;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "UUser")
public class User {

  @Id 
  @GeneratedValue 
  private Long id;
  
  private String name;
  
  private String email;
  
  @OneToOne(cascade = CascadeType.ALL)
  private Location location;
  
  private String password;

  public User() {
    this("", "", null, "");
  }

  public User(String name, String email, Location location, String password) {
    this.name = name;
    this.email = email;
    this.location = location;
    this.password = password;
  }


}
