package pt.ua.tqs.fjmt.marketplace.entities;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashMap;

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

    private String imageLink;

    @OneToOne(cascade = CascadeType.ALL)
    private Price price;

    @OneToOne(cascade = CascadeType.MERGE)
    private Location location;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    private User user;

    public Product() {
    }

    public Product(String name, String category, String description, double price, String imageLink, Location location, User user) {
        this.name = name;
        this.category = category;
        this.Description = description;
        this.price = new Price(price);
        this.imageLink = imageLink;
        this.location = location;
        this.user = user;
    }

    public Product(String name, String category, String description, HashMap<Integer, Double> prices, String imageLink, Location location, User user) {
        this.name = name;
        this.category = category;
        this.Description = description;
        this.price = new Price(prices);
        this.imageLink = imageLink;
        this.location = location;
        this.user = user;
    }

    public Product(String name, String category, String description, Price price, String imageLink, Location location, User user) {
        this.name = name;
        this.category = category;
        this.Description = description;
        this.price = price;
        this.imageLink = imageLink;
        this.location = location;
        this.user = user;
    }

    public Double getPrice() {
        return price.getPrice();
    }

    public Double getPrice(int nDays) {
        return price.getPrice(nDays);
    }

    public HashMap<Integer, Double> getPrices() {
        return price.getPriceMap();
    }

}
