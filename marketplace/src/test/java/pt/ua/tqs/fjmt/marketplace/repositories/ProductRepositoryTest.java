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
import pt.ua.tqs.fjmt.marketplace.entities.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase
@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        this.entityManager.clear();
        this.entityManager.flush();
    }

    @Test
    @DisplayName("After product is on the DB, a query should return it")
    public void whenFindByName_thenReturnProduct() {
        User chico = new User("chico", "", null, "");
        entityManager.persist(chico);
        Product product = new Product("Car", "", "", 0.0, "", null, chico);
        entityManager.persist(product);
        entityManager.flush();

        List<Product> found = productRepository.findByName(product.getName());

        Assertions.assertEquals(found.get(0).getName(), product.getName());
    }

    @Test
    @DisplayName("FindAll should return equal number of products as inserted")
    public void whenFindAll_thenReturnAll() {
        int n_products = 2;
        User chico = new User("chico", "", null, "");
        entityManager.persist(chico);
        Product p1 = new Product("Car", "", "", 0.0, "", null, chico);
        Product p2 = new Product("Car", "", "", 0.0, "", null, chico);
        entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.flush();

        List<Product> found = productRepository.findAll();

        Assertions.assertEquals(n_products, found.size());
    }

    @Test
    @DisplayName("Repository should allow search by category")
    public void whenFindByCategory_thenReturnProduct() {
        User chico = new User("chico", "", null, "");
        entityManager.persist(chico);
        Product p1 = new Product("Car", "Carros", "", 0.0, "", null, chico);
        entityManager.persist(p1);
        entityManager.flush();

        List<Product> found = productRepository.findByCategory(p1.getCategory());

        Assertions.assertEquals(found.get(0).getCategory(), p1.getCategory());
    }

    @Test
    @DisplayName("Repository should allow search by price")
    public void whenFindByPrice_thenReturnProduct() {
        User chico = new User("chico", "", null, "");
        entityManager.persist(chico);
        Product p1 = new Product("Car", "Carros", "", 120.34, "", null, chico);
        entityManager.persist(p1);
        entityManager.flush();

        List<Product> found = productRepository.findByPrice(120.34);

        Assertions.assertEquals(found.get(0).getPrice(), p1.getPrice());
    }

}