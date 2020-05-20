package pt.ua.tqs.fjmt.marketplace.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.tqs.fjmt.marketplace.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public List<User> findAll();
    public User findByName(String name);
    public User findByEmail(String email);

}