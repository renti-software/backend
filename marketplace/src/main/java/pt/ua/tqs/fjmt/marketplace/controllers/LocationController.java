package pt.ua.tqs.fjmt.marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqs.fjmt.marketplace.MarketplaceApplication;
import pt.ua.tqs.fjmt.marketplace.entities.Location;
import pt.ua.tqs.fjmt.marketplace.repositories.LocationRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/locations")
@CrossOrigin(origins = "*")
public class LocationController {

    @Autowired
    LocationRepository locationRepository;

    @GetMapping("")
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Location> findbyId(@PathVariable("id") Long id){
        return locationRepository.findById(id);
    }

    @PostMapping("")
    public Long addLocation(@RequestBody Location location){
        locationRepository.save(location);
        return location.getId();
    }

}
