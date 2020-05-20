package pt.ua.tqs.fjmt.marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqs.fjmt.marketplace.entities.Product;
import pt.ua.tqs.fjmt.marketplace.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/")
    public List<Product> findAll() {return productRepository.findAll();}

    @GetMapping("/{id}")
    public Optional<Product> findbyId(@PathVariable("id") Long id){
        return productRepository.findById(id);
    }

    @GetMapping("/name/{name}")
    public List<Product> findbyName(@PathVariable("name") String name){
        return productRepository.findByName(name);
    }

    @GetMapping("/category/{category}")
    public List<Product> findbyCategory(@PathVariable("category") String category){
        return productRepository.findByCategory(category);
    }

    @GetMapping("/price/{price}")
    public List<Product> findbyPrice(@PathVariable("price") float price){
        return productRepository.findByPrice(price);
    }

    @PostMapping("/")
    public void addProduct(Product product){
        productRepository.save(product);
    }

}
