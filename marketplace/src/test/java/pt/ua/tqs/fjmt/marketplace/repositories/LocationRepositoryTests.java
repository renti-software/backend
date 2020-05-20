package pt.ua.tqs.fjmt.marketplace.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pt.ua.tqs.fjmt.marketplace.entities.Location;
import pt.ua.tqs.fjmt.marketplace.repositories.LocationRepository;

@AutoConfigureTestDatabase
@DataJpaTest
public class LocationRepositoryTests {
    
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private LocationRepository locationRepository;
 
    @BeforeEach
    public void clear_repository() {
        this.entityManager.clear();
        this.entityManager.flush();
    }

    @Test
    @DisplayName("After Location is on the DB, a query should return it")
    public void whenFindByName_thenReturnLocation() {
        // given
        Location aveiro = new Location("Aveiro", "Portugal");
        entityManager.persist(aveiro);
        entityManager.flush();
    
        // when
        Location found = locationRepository.findByCity_name(aveiro.getCity_name());
    
        // then
        Assertions.assertEquals(found.getCity_name(), aveiro.getCity_name());
    }

    @Test
    @DisplayName("FindAll should return equal number of Locations as inserted")
    public void whenFindAll_thenReturnAll() {
        //given
        int n_users = 3;
        Location u1 = new Location();
        Location u2 = new Location();
        Location u3 = new Location();
        entityManager.persist(u1);
        entityManager.persist(u2);
        entityManager.persist(u3);
        entityManager.flush();

        //when
        List<Location> found = locationRepository.findAll();

        //then
        Assertions.assertEquals(n_users, found.size());
    }

    @Test
    @DisplayName("Repository should allow search by Coutry")
    public void whenFindByEmail_thenReturnLocation() {
        // given
        Location aveiro = new Location("Aveiro", "Portugal");
        entityManager.persist(aveiro);
        entityManager.flush();
    
        // when
        List<Location> found = locationRepository.findByCountry(aveiro.getCountry());
    
        // then
        for(Location l : found) {
            Assertions.assertEquals(l.getCountry(), aveiro.getCountry());
        }
    }



}