package pt.ua.tqs.fjmt.marketplace.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pt.ua.tqs.fjmt.marketplace.entities.Favourites;
import pt.ua.tqs.fjmt.marketplace.entities.Product;
import pt.ua.tqs.fjmt.marketplace.repositories.FavouritesRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/favourites")
@CrossOrigin(origins = "*")
@Api(value = "API for Favourites")
public class FavouritesController {
    @Autowired
    FavouritesRepository favouritesRepository;

    @ApiOperation(value = "It will return a list of Favourites for a user")
    @GetMapping("/{userId}")
    public List<Favourites> getUsersFavourites(@ApiParam("Id of the user. Required")
                                                @PathVariable("userId") Long userId){
        List<Favourites> found = favouritesRepository.findByUserId(userId);
        return found;
    }

    @GetMapping("")
    public List<Favourites> findAll(){
        return favouritesRepository.findAll();
    }

    @ApiOperation(value = "It will add a new Favourite to the DB")
    @PostMapping("")
    public Favourites addFavourites(@RequestBody Favourites favourites){
        return favouritesRepository.save(favourites);
    }

    @ApiOperation(value = "It will delete a specific favourite object")
    @DeleteMapping("")
    public String removeFavourites(@RequestBody Favourites favourites){
        try {
            favouritesRepository.delete(favourites);
            return "Success";
        }
        catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Error"
            );
        }
    }
}
