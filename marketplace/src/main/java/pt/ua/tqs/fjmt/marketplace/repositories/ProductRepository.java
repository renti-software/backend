package pt.ua.tqs.fjmt.marketplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.tqs.fjmt.marketplace.entities.Product;
//import pt.ua.tqs.fjmt.marketplace.entities.Location;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findAll();
    public List<Product> findByName(String name);
    public List<Product> findByCategory(String category);
    //public List<Product> findByLocation(Location location);
    public List<Product> findByPrice(Double price);
}
