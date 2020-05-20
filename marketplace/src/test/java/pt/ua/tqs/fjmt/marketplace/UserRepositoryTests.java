package pt.ua.tqs.fjmt.marketplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
 
    @BeforeEach
    public void clear_repository() {
        this.entityManager.clear();
        this.entityManager.flush();
    }

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

    @Test
    @DisplayName("FindAll should return equal number of users as inserted")
    public void whenFindAll_thenReturnAll() {
        //given
        int n_users = 3;
        User u1 = new User();
        User u2 = new User();
        User u3 = new User();
        entityManager.persist(u1);
        entityManager.persist(u2);
        entityManager.persist(u3);
        entityManager.flush();

        //when
        List<User> found = employeeRepository.findAll();

        //then
        Assertions.assertEquals(n_users, found.size());
    }

    @Test
    @DisplayName("Repository should allow search by email")
    public void whenFindByEmail_thenReturnUser() {
        // given
        User alex = new User("alex", "alex@mail.com", null, "");
        entityManager.persist(alex);
        entityManager.flush();
    
        // when
        User found = employeeRepository.findByEmail(alex.getEmail());
    
        // then
        Assertions.assertEquals(found.getEmail(), alex.getEmail());
    }

}