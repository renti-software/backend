package pt.ua.tqs.fjmt.marketplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ua.tqs.fjmt.marketplace.entities.Product;
import pt.ua.tqs.fjmt.marketplace.entities.Rental;
import pt.ua.tqs.fjmt.marketplace.entities.User;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    public List<Rental> findAll();

    List<Rental> findByRenter(User renter);

    List<Rental> findByProduct(Product product);
}
