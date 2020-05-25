package pt.ua.tqs.fjmt.marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import pt.ua.tqs.fjmt.marketplace.entities.User;
import pt.ua.tqs.fjmt.marketplace.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserRepository repository;

    @PostMapping("")
    public User create(@RequestBody User user) {
        return repository.save(user);
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable("id") Long id) {
        Optional<User> u = repository.findById(id);
        if (u.isPresent()) {
            return u;
        }
        else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"
            );
        }
    }

    @GetMapping("")
    public List<User> filterUser(@RequestParam(required = false) String name) {
        List<User> found = new ArrayList<>();
        if (name != null) {
            found = repository.findByName(name);
            if (found.size() == 0) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found"
                );
            }
        }
        else {
            found = repository.findAll();
        }
        return found;
    }

    @PutMapping("")
    public User editUser(@RequestBody User user) {
        return repository.save(user);
    }

    @DeleteMapping("")
    public String removeUser(@RequestBody User user) {
        try {
            repository.delete(user);
            return "Success";
        }
        catch (Exception e){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error"
            );
        }
    }

}
