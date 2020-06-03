package pt.ua.tqs.fjmt.marketplace.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pt.ua.tqs.fjmt.marketplace.entities.Product;
import pt.ua.tqs.fjmt.marketplace.entities.Rental;
import pt.ua.tqs.fjmt.marketplace.entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase
@DataJpaTest
class RentalRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RentalRepository rentalRepository;

    @BeforeEach
    void setUp() {
        this.entityManager.clear();
        this.entityManager.flush();
    }

    @Test
    @DisplayName("FindAll should return equal number of rentals as inserted")
    public void whenFindAll_thenReturnAll() {
        int n_rentals = 2;

        User chico = new User("chico", "", null, "");
        entityManager.persist(chico);
        User chico2 = new User("chico2", "", null, "");
        entityManager.persist(chico2);
        Product p1 = new Product("Car", "", "", 0.0, "", null, chico);
        entityManager.persist(p1);
        Product p2 = new Product("Car", "", "", 0.0, "", null, chico2);
        entityManager.persist(p2);

        Rental r1 = new Rental(chico2, p1, LocalDate.now(), LocalDate.now());
        Rental r2 = new Rental(chico, p2, LocalDate.now(), LocalDate.now());
        entityManager.persist(r1);
        entityManager.persist(r2);

        entityManager.flush();

        List<Rental> found = rentalRepository.findAll();

        Assertions.assertEquals(n_rentals, found.size());
    }

    @Test
    @DisplayName("Repository should allow search by product")
    public void whenFindByProduct_thenReturnRental() {
        User chico = new User("chico", "", null, "");
        entityManager.persist(chico);
        User chico2 = new User("chico2", "", null, "");
        entityManager.persist(chico2);
        Product product = new Product("Car", "", "", 0.0, "",  null, chico);
        entityManager.persist(product);
        Rental rental = new Rental(chico2, product, LocalDate.now(), LocalDate.now());
        entityManager.persist(rental);
        entityManager.flush();

        List<Rental> found = rentalRepository.findByProduct(rental.getProduct());

        Assertions.assertEquals(found.get(0), rental);
    }

    @Test
    @DisplayName("Repository should allow search by renter")
    public void whenFindByRenter_thenReturnProduct() {
        User chico = new User("chico", "", null, "");
        entityManager.persist(chico);
        User chico2 = new User("chico2", "", null, "");
        entityManager.persist(chico2);
        Product product = new Product("Car", "", "", 0.0, "", null, chico);
        entityManager.persist(product);
        Rental rental = new Rental(chico2, product, LocalDate.now(), LocalDate.now());
        entityManager.persist(rental);
        entityManager.flush();

        List<Rental> found = rentalRepository.findByRenter(rental.getRenter());

        Assertions.assertEquals(found.get(0), rental);
    }

    @Test
    @DisplayName("Repository should allow search by id")
    public void whenFindbyId_thenReturnUser() {
        //given
        Rental r = new Rental();
        entityManager.persist(r);
        entityManager.flush();

        //when
        Optional<Rental> found = rentalRepository.findById(r.getId());

        //then
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(r.getId(), found.get().getId());
    }

    @Test
    @DisplayName("Repository should return nothing when Rental does not exist")
    public void whenFindbyId_thenReturnNone() {
        //given
        // entityManager cleared

        //when
        Optional<Rental> found = rentalRepository.findById((long) 1);

        //then
        Assertions.assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Repository should allow search by approval state")
    public void whenFindByApproval_thenReturnRental() {
        User chico = new User("chico", "", null, "");
        entityManager.persist(chico);
        User chico2 = new User("chico2", "", null, "");
        entityManager.persist(chico2);
        Product product = new Product("Car", "", "", 0.0, "", null, chico);
        entityManager.persist(product);
        Rental rental = new Rental(chico2, product, LocalDate.now(), LocalDate.now());
        entityManager.persist(rental);
        entityManager.flush();

        List<Rental> found = rentalRepository.findByApproved(false);

        Assertions.assertEquals(found.get(0), rental);
    }

    @Test
    @DisplayName("Repository should allow search by approval state true")
    public void whenFindByApprovalTrue_thenReturnRental() {
        User chico = new User("chico", "", null, "");
        entityManager.persist(chico);
        User chico2 = new User("chico2", "", null, "");
        entityManager.persist(chico2);
        Product product = new Product("Car", "", "", 0.0, "", null, chico);
        entityManager.persist(product);
        Rental rental = new Rental(chico2, product, LocalDate.now(), LocalDate.now());
        rental.setApproved(true);
        entityManager.persist(rental);
        entityManager.flush();

        List<Rental> found = rentalRepository.findByApproved(true);

        Assertions.assertEquals(found.get(0), rental);
    }
}