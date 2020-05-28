package pt.ua.tqs.fjmt.marketplace.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Authenticator {
    
  @Id 
  @GeneratedValue 
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
  User user;

  String token;

  public Authenticator(User user) {
      this.user = user;
      this.token = user.getId().toString();
  }

}