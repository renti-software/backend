package pt.ua.tqs.fjmt.marketplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ua.tqs.fjmt.marketplace.entities.Product;
import pt.ua.tqs.fjmt.marketplace.entities.Rental;
import pt.ua.tqs.fjmt.marketplace.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    public List<Rental> findAll();
    public List<Rental> findByRenter(User renter);
    public List<Rental> findByRenterId(Long id);
    public List<Rental> findByProduct(Product product);
    public List<Rental> findByProductId(Long id);
    public List<Rental> findByProductUser(User user);
    public List<Rental> findByProductUserId(Long id);
    public List<Rental> findByApproved(Boolean approved);
    public Optional<Rental> findById(Long id);

}
