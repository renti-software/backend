package pt.ua.tqs.fjmt.marketplace.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pt.ua.tqs.fjmt.marketplace.MarketplaceApplication;
import pt.ua.tqs.fjmt.marketplace.entities.Product;
import pt.ua.tqs.fjmt.marketplace.repositories.AuthenticatorRepository;
import pt.ua.tqs.fjmt.marketplace.repositories.ProductRepository;
import pt.ua.tqs.fjmt.marketplace.services.ProductService;

import java.util.*;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
@Api(value = "API for Products")
public class ProductController {

    @Autowired
    ProductService productService;

    @ApiOperation(value = "It will return a list of Products that can be filtered and/or ordered. If no parameter is passed returns all products")
    @GetMapping("")
    public List<Product> findProductList(@ApiParam("Name of the product to be searched. Optional")
                                             @RequestParam(required = false, name = "name") String name,
                                         @ApiParam("City name of the product to be searched. Optional")
                                            @RequestParam(required = false, name = "location") String location,
                                         @ApiParam("Category of the product to be searched. Optional")
                                             @RequestParam(required = false, name = "category") String category,
                                         @ApiParam("Minimum price of the product to be searched. Optional, but only works in combination with maxPrice")
                                             @RequestParam(required = false, name = "minPrice") Double minPrice,
                                         @ApiParam("Maximum of the product to be searched. Optional, but only works in combination with minPrice")
                                             @RequestParam(required = false, name = "maxPrice") Double maxPrice,
                                         @ApiParam("Id of owner of the product to be searched. Optional")
                                             @RequestParam(required = false, name = "userId") Long userId,
                                         @ApiParam("Parameter to order list of products. Can have values name and price. Optional, but only works in combination with order")
                                             @RequestParam(required = false, name = "orderParameter") String orderParameter,
                                         @ApiParam("Defines order sequence of list of products. Can have values asc and desc. Optional, but only works in combination with orderParameter")
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

        if(userId != null){
            found = productService.filterByUserId(found, userId);
        }

        if(orderParameter != null && order != null){
            found = productService.orderByParameter(found, orderParameter, order);
        }


        if(found == null){
            found = new ArrayList<>();
        }
        return found;
    }
    @ApiOperation(value = "It will return a Product")
    @GetMapping("/{id}")
    public Product findById(@ApiParam("Id of the product. Cannot be empty")
                                @PathVariable("id") Long id){
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
    @ApiOperation(value = "It will return the calculated price of a Product based on the number of days")
    @GetMapping("/{id}/price")
    public double findProductPriceForRental(@ApiParam("Id of the product. Cannot be empty")
                                                @PathVariable("id") Long id,
                                            @ApiParam("Start date of rental. Required")
                                                @RequestParam(name = "startDate") String startDate,
                                            @ApiParam("End date of rental. Required")
                                                @RequestParam(name = "endDate") String endDate){
        Optional<Product> found = productService.findProductById(id);
        if(found.isPresent()){
            Product p = found.get();
            return productService.getCalculatedProductPrice(p, startDate, endDate);
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Product not found"
            );
        }

    }

    @ApiOperation(value = "It will save a new Product in the database and return that same Product")
    @PostMapping("")
    public Product addProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }

    @ApiOperation(value = "It will update the product and return it")
    @PutMapping("")
    public Product editProduct(@RequestBody Product product) {
        Optional<Product> p = productService.findProductById(product.getId());
        if (!p.isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error"
            );
        }
        Product dbProduct = p.get();
        copyNonNullProperties(product, dbProduct);
        return productService.saveProduct(dbProduct);
    }

    @ApiOperation(value = "It will delete the specific product")
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

    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
