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
    @GeneratedValue
    private Long id;

    private String name;
    private String category;
    private String Description;
    private float price_per_hour;

    @OneToOne(cascade = CascadeType.ALL)
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Product() {this("", "", "", 0.0f, null, null);}

    public Product(String name, String category, String description, float price_per_hour, Location location, User user) {
        this.name = name;
        this.category = category;
        Description = description;
        this.price_per_hour = price_per_hour;
        this.location = location;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public float getPrice_per_hour() {
        return price_per_hour;
    }

    public void setPrice_per_hour(float price_per_hour) {
        this.price_per_hour = price_per_hour;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
