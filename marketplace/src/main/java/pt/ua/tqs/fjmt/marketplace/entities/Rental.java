package pt.ua.tqs.fjmt.marketplace.entities;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "Rental")
public class Rental {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User renter;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade=CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    private LocalDate startDate;
    private LocalDate endDate;

    public Rental() {this(null, null, null, null);}

    public Rental(User renter, Product product, LocalDate startDate, LocalDate endDate) {
        this.renter = renter;
        this.product = product;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
