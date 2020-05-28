package pt.ua.tqs.fjmt.marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import pt.ua.tqs.fjmt.marketplace.entities.Authenticator;
import pt.ua.tqs.fjmt.marketplace.entities.User;
import pt.ua.tqs.fjmt.marketplace.repositories.AuthenticatorRepository;
import pt.ua.tqs.fjmt.marketplace.repositories.UserRepository;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class AuthenticatorController {

    @Autowired
    AuthenticatorRepository authenticatorRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("")
    public Authenticator login(String email, String password){
        User user = userRepository.findByEmail(email).get(0);
        return authenticatorRepository.save(new Authenticator(user));
    }

    @DeleteMapping("")
    public String logout(@RequestBody User user) {
        try {
            for (Authenticator a : authenticatorRepository.findByUser(user))
                authenticatorRepository.delete(a);
            return "Success";
        }
        catch (Exception e){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error"
            );
        }
    }

}