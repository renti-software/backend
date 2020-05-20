package pt.ua.tqs.fjmt.marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pt.ua.tqs.fjmt.marketplace.entities.User;
import pt.ua.tqs.fjmt.marketplace.repositories.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserRepository repository;


    // @PutMapping("/")
    // public String create(@RequestBody Localizacao localizacao) {
    //     repository.save(localizacao);
    //     return "Localização criada";
    // }

    @GetMapping("/")
    public List<User> findAll() {
        List<User> found = repository.findAll();
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
