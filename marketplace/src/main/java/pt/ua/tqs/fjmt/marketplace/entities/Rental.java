package pt.ua.tqs.fjmt.marketplace.entities;

import lombok.Data;
import net.bytebuddy.asm.Advice.Local;

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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    private User renter;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    private Product product;

    private LocalDate startDate;
    private LocalDate endDate;

    public Rental() {
    }

    public Rental(User renter, Product product, LocalDate startDate, LocalDate endDate) {
        this.renter = renter;
        this.product = product;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Rental(User renter, Product product) {
        this(renter, product, null, null);
    }

}
