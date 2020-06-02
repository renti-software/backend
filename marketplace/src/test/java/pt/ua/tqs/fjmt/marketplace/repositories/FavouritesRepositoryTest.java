package pt.ua.tqs.fjmt.marketplace.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pt.ua.tqs.fjmt.marketplace.entities.Favourites;
import pt.ua.tqs.fjmt.marketplace.entities.Product;
import pt.ua.tqs.fjmt.marketplace.entities.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase
@DataJpaTest
class FavouritesRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private  FavouritesRepository favouritesRepository;

    @BeforeEach
    void setUp() {
        this.entityManager.clear();
        this.entityManager.flush();
    }

    @Test
    @DisplayName("After favourite is on the DB, a query should return it")
    public void whenFindByName_thenReturnProduct() {
        User chico = new User("chico", "", null, "");
        entityManager.persist(chico);
        Product product = new Product("Car", "", "", 0.0, "", null, chico);
        entityManager.persist(product);
        Favourites f = new Favourites(chico, product);
        entityManager.persist(f);

        entityManager.flush();

        List<Favourites> found = favouritesRepository.findAll();

        Assertions.assertEquals(found.get(0).getUser().getName(), chico.getName());
        Assertions.assertEquals(found.get(0).getProduct().getName(), product.getName());
    }
}