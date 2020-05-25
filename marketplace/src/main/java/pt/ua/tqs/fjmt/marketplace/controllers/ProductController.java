package pt.ua.tqs.fjmt.marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pt.ua.tqs.fjmt.marketplace.MarketplaceApplication;
import pt.ua.tqs.fjmt.marketplace.entities.Product;
import pt.ua.tqs.fjmt.marketplace.repositories.ProductRepository;
import pt.ua.tqs.fjmt.marketplace.services.ProductService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("")
    public List<Product> findProductList(@RequestParam(required = false, name = "name") String name,
                                 @RequestParam(required = false, name = "location") String location,
                                 @RequestParam(required = false, name = "category") String category,
                                 @RequestParam(required = false, name = "minPrice") Double minPrice,
                                 @RequestParam(required = false, name = "maxPrice") Double maxPrice) {
        List<Product> found = productService.findAll();

        if(name != null){
            productService.filterByName(found, name);
        }

        if(location != null){
            productService.filterByLocation(found, location);
        }

        if(category != null){
            productService.filterByCategory(found, category);
        }

        if(minPrice != null && maxPrice != null){
            productService.filterByPriceRange(found, minPrice, maxPrice);
        }
        return found;
    }

    @GetMapping("/{id}")
    public Optional<Product> findById(@PathVariable("id") Long id){
        return productService.findProductById(id);
    }


    @PostMapping("")
    public Product addProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }

}
