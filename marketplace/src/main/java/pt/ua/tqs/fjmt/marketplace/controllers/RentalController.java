package pt.ua.tqs.fjmt.marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import pt.ua.tqs.fjmt.marketplace.entities.Product;
import pt.ua.tqs.fjmt.marketplace.entities.Rental;
import pt.ua.tqs.fjmt.marketplace.entities.User;
import pt.ua.tqs.fjmt.marketplace.repositories.RentalRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rentals")
@CrossOrigin(origins = "*")
public class RentalController {

    @Autowired
    RentalRepository rentalRepository;

    @GetMapping("")
    public List<Rental> filter(@RequestParam(required = false) User renter,
                               @RequestParam(required = false) Product product
                            ) {

        List<Rental> found = new ArrayList<>();
        if (renter != null) {
            found = rentalRepository.findByRenter(renter);
            if (found.size() == 0) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rentals not found for this Renter"
                );
            }
        }
        else if (product != null) {
            found = rentalRepository.findByProduct(product);
            if (found.size() == 0) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rentals not found for this Product"
                );
            }
        }
        else {
            found = rentalRepository.findAll();
        }
        return found;
    }

    @GetMapping("/{id}")
    public Optional<Rental> findById(@PathVariable("id") Long id){
        Optional<Rental> found = rentalRepository.findById(id);
        if (found.isPresent()) {
            return found;
        }
        else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Rental not found"
            );
        }
    }

    @PostMapping("")
    public Long addRental(Rental rental){
        rentalRepository.save(rental);
        return rental.getId();
    }
}
