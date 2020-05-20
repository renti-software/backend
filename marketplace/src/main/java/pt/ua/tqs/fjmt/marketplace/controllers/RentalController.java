package pt.ua.tqs.fjmt.marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqs.fjmt.marketplace.entities.Product;
import pt.ua.tqs.fjmt.marketplace.entities.Rental;
import pt.ua.tqs.fjmt.marketplace.entities.User;
import pt.ua.tqs.fjmt.marketplace.repositories.RentalRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rentals")
@CrossOrigin(origins = "*")
public class RentalController {
    @Autowired
    RentalRepository rentalRepository;

    @GetMapping("")
    public List<Rental> findAll(){
        return rentalRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Rental> findById(@PathVariable("id") Long id){
        return rentalRepository.findById(id);
    }

    @GetMapping("/renter/{user}")
    public List<Rental> findByRenter(@PathVariable("user") User user){
        return rentalRepository.findByRenter(user);
    }

    @GetMapping("/product/{product}")
    public List<Rental> findByProduct(@PathVariable("product") Product product){
        return rentalRepository.findByProduct(product);
    }

    @PostMapping("")
    public String addRental(Rental rental){
        rentalRepository.save(rental);
        return "New rental added";
    }
}
