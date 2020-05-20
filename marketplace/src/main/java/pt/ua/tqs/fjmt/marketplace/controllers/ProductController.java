package pt.ua.tqs.fjmt.marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqs.fjmt.marketplace.MarketplaceApplication;
import pt.ua.tqs.fjmt.marketplace.entities.Product;
import pt.ua.tqs.fjmt.marketplace.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("")
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

    @GetMapping("/parameters")
    public List<Product> findProductWithParameters(@RequestParam(required = false, name = "location") String location,
                                                   @RequestParam(required = false, name = "category") String category,
                                                   @RequestParam(required = false, name = "minPrice") float minPrice,
                                                   @RequestParam(required = false, name = "maxPrice") float maxPrice){

        List<Product> found = productRepository.findAll();
        Iterator<Product> iterator;

        if(location != null){
            iterator = found.iterator();
            while(iterator.hasNext()){
                Product product = iterator.next();
                if(!product.getLocation().getCityName().equalsIgnoreCase(location)){
                    iterator.remove();
                }
            }
        }

        if(category != null){
            iterator = found.iterator();
            while(iterator.hasNext()){
                Product product = iterator.next();
                if(!product.getCategory().equalsIgnoreCase(category)){
                    iterator.remove();
                }
            }
        }

        if(minPrice != 0.0f && maxPrice != 0.0f){
            iterator = found.iterator();
            while(iterator.hasNext()){
                Product product = iterator.next();
                float price = product.getPrice();
                if(price < minPrice || price > maxPrice){
                    iterator.remove();
                }
            }
        }
        return found;
    }

    @PostMapping("")
    public void addProduct(Product product){
        productRepository.save(product);
    }

}
