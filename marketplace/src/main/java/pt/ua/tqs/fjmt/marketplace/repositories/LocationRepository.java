package pt.ua.tqs.fjmt.marketplace.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.tqs.fjmt.marketplace.entities.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    public List<Location> findAll();
    public Location findByName(String name);

}