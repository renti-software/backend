package pt.ua.tqs.fjmt.marketplace.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import pt.ua.tqs.fjmt.marketplace.entities.Product;
import pt.ua.tqs.fjmt.marketplace.entities.Rental;
import pt.ua.tqs.fjmt.marketplace.entities.User;
import pt.ua.tqs.fjmt.marketplace.repositories.RentalRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/rentals")
@CrossOrigin(origins = "*")
@Api(value = "API for Rentals")
public class RentalController {

    @Autowired
    RentalRepository rentalRepository;

    @ApiOperation(value = "It will return list of Rentals for a user or a product")
    @GetMapping("")
    public List<Rental> filter(@ApiParam(value = "Renter of the rentals")
                                   @RequestParam(required = false, name = "renterId") Long renterId,
                               @ApiParam(value = "Product included in the rentals")
                                    @RequestParam(required = false, name = "productId") Long productId,
                               @ApiParam(value = "Rental approval state")
                                    @RequestParam(required = false, name = "approved") Boolean approved,
                               @ApiParam(value = "Product owner")
                                    @RequestParam(required = false, name = "ownerId") Long ownerId
                            ) {

        Boolean all = true;
        List<Rental> found = null;
        if (renterId != null) {
            all = false;
            found = rentalRepository.findByRenterId(renterId);
            if (found.size() == 0) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rentals not found for this Renter"
                );
            }
        }
        if (productId != null) {
            all = false;
            List<Rental> newFound = rentalRepository.findByProductId(productId);
            if (found == null) {
                found = new ArrayList<Rental>(newFound);
            }
            found.retainAll(newFound);
            if (found.size() == 0) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rentals not found for this Product"
                );
            }
        }
        if (approved != null) {
            all = false;
            List<Rental> newFound = rentalRepository.findByApproved(approved);
            if (found == null) {
                found = new ArrayList<Rental>(newFound);
            }
            found.retainAll(newFound);
            if (found.size() == 0) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rentals not found for this approval state"
                );
            }
        }
        if (ownerId != null) {
            all = false;
            List<Rental> newFound = rentalRepository.findByProductUserId(ownerId);
            if (found == null) {
                found = new ArrayList<Rental>(newFound);
            }
            found.retainAll(newFound);
            if (found.size() == 0) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rentals not found for this Product owner"
                );
            }
        }
        if (all) {
            found = rentalRepository.findAll();
        }
        return found;
    }

    @ApiOperation(value = "It will return Rental by id")
    @GetMapping("/{id}")
    public Rental findById(@PathVariable("id") Long id){
        Optional<Rental> found = rentalRepository.findById(id);
        if (found.isPresent()) {
            return found.get();
        }
        else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Rental not found"
            );
        }
    }

    @ApiOperation(value = "It will return the product associated to a specific Rental")
    @GetMapping("/{id}/product")
    public Product getProduct(@PathVariable("id") Long id){
        Optional<Rental> found = rentalRepository.findById(id);
        System.out.println(found);
        if (found.isPresent()) {
            return found.get().getProduct();
        }
        else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Rental not found"
            );
        }
    }

    @ApiOperation(value = "It will return the renter associated to a specific Rental")
    @GetMapping("/{id}/renter")
    public User getRenter(@PathVariable("id") Long id){
        Optional<Rental> found = rentalRepository.findById(id);
        if (found.isPresent()) {
            return found.get().getRenter();
        }
        else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Rental not found"
            );
        }
    }

    @ApiOperation(value = "It will save a new rental")
    @PostMapping("")
    public Rental addRental(@RequestBody Rental rental){
        return rentalRepository.save(rental);
    }

    @ApiOperation(value = "It will update an existing rental")
    @PutMapping("")
    public Rental editRental(@RequestBody Rental rental) {
        Optional<Rental> r = rentalRepository.findById(rental.getId());
        if (!r.isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Rental Not Found"
            );
        }
        Rental dbRental = r.get();
        copyNonNullProperties(rental, dbRental);
        return rentalRepository.save(dbRental);
    }

    @ApiOperation(value = "It will delete an existing rental")
    @DeleteMapping("")
    public String removeRental(@RequestBody Rental rental) {
        try {
            rentalRepository.delete(rental);
            return "Success";
        }
        catch (Exception e){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error"
            );
        }
    }

    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
