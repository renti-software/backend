package pt.ua.tqs.fjmt.marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import pt.ua.tqs.fjmt.marketplace.MarketplaceApplication;
import pt.ua.tqs.fjmt.marketplace.entities.Location;
import pt.ua.tqs.fjmt.marketplace.entities.Product;
import pt.ua.tqs.fjmt.marketplace.repositories.ProductRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    private Iterator<Product> iterator;

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public void delete(Product product) throws Exception {
        productRepository.delete(product);
    }

    public Optional<Product> findProductById(Long Id){
        return productRepository.findById(Id);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public List<Product> filterByLocation(List<Product> found, String location){
        iterator = found.iterator();
        while(iterator.hasNext()){
            Product product = iterator.next();
            if(!product.getLocation().getCityName().equalsIgnoreCase(location)){
                iterator.remove();
            }
        }
        return found;
    }

    public List<Product> filterByCategory(List<Product> found, String category){
        iterator = found.iterator();
        while(iterator.hasNext()){
            Product product = iterator.next();
            if(!product.getCategory().equalsIgnoreCase(category)){
                iterator.remove();
            }
        }
        return found;
    }

    public List<Product> filterByPriceRange(List<Product> found, double minPrice, double maxPrice){
        iterator = found.iterator();
        while(iterator.hasNext()){
            Product product = iterator.next();
            double price = product.getPrice();
            if(price < minPrice || price > maxPrice){
                iterator.remove();
            }
        }
        return found;
    }

    public List<Product> filterByName(List<Product> found, String name) {
        iterator = found.iterator();
        while(iterator.hasNext()){
            Product product = iterator.next();
            String productName = product.getName();
            if(!productName.contains(name)){
                iterator.remove();
            }
        }
        return found;
    }

    public List<Product> orderByParameter(List<Product> found, String orderParameter, String order) {
        if(orderParameter.equalsIgnoreCase("name")){
            if(order.equalsIgnoreCase("desc")){
                Collections.sort(found, Product.ProdNameComparatorDesc);
            }
            else if(order.equalsIgnoreCase("asc")){
                Collections.sort(found, Product.ProdNameComparatorAsc);
            }
        }

        if(orderParameter.equalsIgnoreCase("price")){
            if(order.equalsIgnoreCase("desc")){
                Collections.sort(found, Product.ProdPriceComparatorDesc);
            }
            else if(order.equalsIgnoreCase("asc")){
                Collections.sort(found, Product.ProdPriceComparatorAsc);
            }
        }
        return found;
    }

    public double getCalculatedProductPrice(Product p, String startDate, String endDate) {
        double price = p.getPrice();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale( Locale.US );
        LocalDate startDate2 = LocalDate.parse(startDate, formatter);
        LocalDate endDate2 = LocalDate.parse(endDate, formatter);
        long days = DAYS.between(startDate2, endDate2);

        return price*days;
    }

    public List<Product> filterByUserId(List<Product> found, Long userId) {
        iterator = found.iterator();
        while(iterator.hasNext()){
            Product product = iterator.next();
            Long uid = product.getUser().getId();
            if(uid != userId){
                iterator.remove();
            }
        }
        return found;
    }
}
