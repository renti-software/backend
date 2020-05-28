package pt.ua.tqs.fjmt.marketplace.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.tqs.fjmt.marketplace.entities.Authenticator;
import pt.ua.tqs.fjmt.marketplace.entities.User;

@Repository
public interface AuthenticatorRepository extends JpaRepository<Authenticator, Long> {

    public List<Authenticator> findAll();
    public List<Authenticator> findByUser(User user);

}