package pt.ua.tqs.fjmt.marketplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pt.ua.tqs.fjmt.marketplace.entities.User;
import pt.ua.tqs.fjmt.marketplace.repositories.UserRepository;

@AutoConfigureTestDatabase
@DataJpaTest
public class UserRepositoryTests {
    
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private UserRepository employeeRepository;
 
    // write test cases here

    @Test
    @DisplayName("After user is on the DB, a query should return it")
    public void whenFindByName_thenReturnUser() {
        // given
        User alex = new User("alex", "", null, "");
        entityManager.persist(alex);
        entityManager.flush();
    
        // when
        User found = employeeRepository.findByName(alex.getName());
    
        // then
        Assertions.assertEquals(found.getName(), alex.getName());
    }

}