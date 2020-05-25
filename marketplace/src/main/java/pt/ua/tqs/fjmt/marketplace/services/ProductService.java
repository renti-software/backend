package pt.ua.tqs.fjmt.marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.tqs.fjmt.marketplace.entities.Location;
import pt.ua.tqs.fjmt.marketplace.entities.Product;
import pt.ua.tqs.fjmt.marketplace.repositories.ProductRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    private Iterator<Product> iterator;

    public Product saveProduct(Product product){
        return productRepository.save(product);
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
}
