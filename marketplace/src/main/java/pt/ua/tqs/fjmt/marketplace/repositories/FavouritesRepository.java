package pt.ua.tqs.fjmt.marketplace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.ua.tqs.fjmt.marketplace.entities.Favourites;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouritesRepository extends JpaRepository<Favourites, Long> {
    public List<Favourites> findAll();
    public Optional<Favourites> findById(Long id);
    public List<Favourites> findByUserId(Long id);
}
