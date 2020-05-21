package pt.ua.tqs.fjmt.marketplace.entities;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;
    
    private String Description;

    private float price;

    @OneToOne(cascade = CascadeType.MERGE)
    private Location location;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    private User user;

    public Product() {
    }

    public Product(String name, String category, String description, float price, Location location, User user) {
        this.name = name;
        this.category = category;
        this.Description = description;
        this.price = price;
        this.location = location;
        this.user = user;
    }

}
