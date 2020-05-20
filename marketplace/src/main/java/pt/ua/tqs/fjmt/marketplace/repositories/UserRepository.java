package pt.ua.tqs.fjmt.marketplace.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.tqs.fjmt.marketplace.entities.Location;
import pt.ua.tqs.fjmt.marketplace.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public List<User> findAll();
    public Optional<User> findById(Long id);
    public List<User> findByName(String name);
    public List<User> findByEmail(String email);
    public List<User> findByLocation(Location location);

}