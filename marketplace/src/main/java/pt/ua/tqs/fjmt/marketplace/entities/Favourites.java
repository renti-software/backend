package pt.ua.tqs.fjmt.marketplace.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Favourites")
public class Favourites {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    private Product product;

    public Favourites(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    public Favourites() {
    }
}
