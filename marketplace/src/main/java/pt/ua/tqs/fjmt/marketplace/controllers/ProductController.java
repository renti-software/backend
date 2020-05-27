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
                                         @RequestParam(required = false, name = "maxPrice") Double maxPrice,
                                         @RequestParam(required = false, name = "orderParameter") String orderParameter,
                                         @RequestParam(required = false, name = "order") String order) {

        List<Product> found = productService.findAll();

        if(name != null){
            found = productService.filterByName(found, name);
        }

        if(location != null){
            found = productService.filterByLocation(found, location);
        }

        if(category != null){
            found = productService.filterByCategory(found, category);
        }

        if(minPrice != null && maxPrice != null){
            found = productService.filterByPriceRange(found, minPrice, maxPrice);
        }

        if(orderParameter != null && order != null){
            found = productService.orderByParameter(found, orderParameter, order);
        }

        if(found == null){
            found = new ArrayList<>();
        }
        return found;
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable("id") Long id){
        Optional<Product> found = productService.findProductById(id);
        if(found.isPresent()){
            return found.get();
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Product not found"
            );
        }
    }


    @PostMapping("")
    public Product addProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }

    @PutMapping("")
    public Product editProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @DeleteMapping("")
    public String removeProduct(@RequestBody Product product) {
        try {
            productService.delete(product);
            return "Success";
        }
        catch (Exception e){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error"
            );
        }
    }
}
