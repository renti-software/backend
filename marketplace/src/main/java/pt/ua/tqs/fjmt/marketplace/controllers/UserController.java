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

    @PostMapping("/")
    public Long create(@RequestBody User user) {
        repository.save(user);
        return user.getId();
    }

    @GetMapping("")
    public List<User> getUserByName(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) Long id) {
        List<User> found = new ArrayList<>();
        if (name != null) {
            found = repository.findByName(name);
        }
        else if (id != null) {
            Optional<User> u = repository.findById(id);
            if (u.isPresent()) {
                found.add(u.get());
            }
        }
        else {
            found = repository.findAll();
        }
        if (found.size() == 0) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"
            );
        }
        return found;
    }

    // @DeleteMapping("/")
    // public String delete(@RequestBody Localizacao localizacao) {
    //     repository.delete(localizacao);
    //     return "Localização removida";
    // }

    // @GetMapping("/searchbycoordenadas/{latitude}/{longitude}")
    // public List<Localizacao> findbyCoordinates(@PathVariable float latitude, @PathVariable float longitude) {
    //     return repository.findByLatitudeAndLongitude(latitude, longitude);
    // }

    // // Procura por cidade ou rua
    // @GetMapping("/searchLocal/{local}")
    // public List<Localizacao> findByCity(@PathVariable String local) {
    //     List<Localizacao> localizacoes = repository.findByCidadeContaining(local);
    //     localizacoes.addAll(repository.findByMoradaContaining(local));
    //     return localizacoes;
    // }

    // @GetMapping("/searchByCity/{cidade}")
    // public List<Localizacao> find(@PathVariable String cidade) {
    //     return repository.findByCidadeLike(cidade);
    // }

}
