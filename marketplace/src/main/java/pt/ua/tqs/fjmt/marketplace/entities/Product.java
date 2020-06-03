package pt.ua.tqs.fjmt.marketplace.entities;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Comparator;
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

    private Double price;

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
        this.price = price;
        this.imageLink = imageLink;
        this.location = location;
        this.user = user;
    }

    public Product(String name, String category, String description, int price, String imageLink, Location location, User user) {
        this.name = name;
        this.category = category;
        this.Description = description;
        this.price = Double.valueOf(price);
        this.imageLink = imageLink;
        this.location = location;
        this.user = user;
    }

    public static Comparator<Product> ProdNameComparatorAsc = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            String name1 = p1.getName();
            String name2 = p2.getName();
            return name1.compareTo(name2);
        }
    };

    public static Comparator<Product> ProdNameComparatorDesc = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            String name1 = p1.getName();
            String name2 = p2.getName();
            return name2.compareTo(name1);
        }
    };

    public static Comparator<Product> ProdPriceComparatorAsc = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            Double price1 = p1.getPrice();
            Double price2 = p2.getPrice();
            return price1.compareTo(price2);
        }
    };

    public static Comparator<Product> ProdPriceComparatorDesc = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            Double price1 = p1.getPrice();
            Double price2 = p2.getPrice();
            return price2.compareTo(price1);
        }
    };

    public Double getPrice() {
        return price;
    }

}
