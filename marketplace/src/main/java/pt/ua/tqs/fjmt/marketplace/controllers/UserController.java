package pt.ua.tqs.fjmt.marketplace.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(value = "API for Users")
public class UserController {

    @Autowired
    UserRepository repository;

    @ApiOperation(value = "It will save a new user")
    @PostMapping("")
    public User create(@RequestBody User user) {
        return repository.save(user);
    }

    @ApiOperation(value = "It will return a user based on its id")
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

    @ApiOperation(value = "It will return a list of users that have the same name")
    @GetMapping("")
    public List<User> filterUser(@ApiParam(value = "Name of the user. Optional, in this case returns all users")
                                     @RequestParam(required = false) String name) {
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

    @ApiOperation(value = "It will update an existing user")
    @PutMapping("")
    public User editUser(@RequestBody User user) {
        return repository.save(user);
    }

    @ApiOperation(value = "It will delete an existing user")
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
