package pt.ua.tqs.fjmt.marketplace.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import pt.ua.tqs.fjmt.marketplace.entities.Location;
import pt.ua.tqs.fjmt.marketplace.entities.Product;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Mock
    Product p1;

    @Mock
    Product p2;

    @Mock
    Product p3;

    @BeforeEach
    void setUp() {
        productService = new ProductService();

        p1 = Mockito.mock(Product.class);
        p2 = Mockito.mock(Product.class);
        p3 = Mockito.mock(Product.class);

        //define getName()
        Mockito.when(p1.getName()).thenReturn("Carro");
        Mockito.when(p2.getName()).thenReturn("Cao");
        Mockito.when(p3.getName()).thenReturn("Arroz");

        //define getCategory()
        Mockito.when(p1.getCategory()).thenReturn("Automovel");
        Mockito.when(p2.getCategory()).thenReturn("Animal");
        Mockito.when(p3.getCategory()).thenReturn("Comida");

        //define getPrice
        Mockito.when(p1.getPrice()).thenReturn(20.0);
        Mockito.when(p2.getPrice()).thenReturn(120.23);
        Mockito.when(p3.getPrice()).thenReturn(40.34);

        //define getLocation()
        Mockito.when(p1.getLocation()).thenReturn((new Location("Aveiro", "Portugal")));
        Mockito.when(p2.getLocation()).thenReturn((new Location("Porto", "Portugal")));
        Mockito.when(p3.getLocation()).thenReturn((new Location("Faro", "Portugal")));

    }

    @Test
    void filterByLocation() {
        List<Product> testList = new ArrayList<>();
        testList.add(p1);
        testList.add(p2);
        testList.add(p3);

        productService.filterByLocation(testList, "Aveiro");
        Assertions.assertEquals(testList.size(), 1);
    }

    @Test
    void filterByCategory() {
        List<Product> testList = new ArrayList<>();
        testList.add(p1);
        testList.add(p2);
        testList.add(p3);

        productService.filterByCategory(testList, "Animal");
        Assertions.assertEquals(testList.size(), 1);
    }

    @Test
    void filterByPriceRange() {
        List<Product> testList = new ArrayList<>();
        testList.add(p1);
        testList.add(p2);
        testList.add(p3);

        productService.filterByPriceRange(testList, 40, 300);
        Assertions.assertEquals(testList.size(), 2);
    }

    @Test
    void filterByName() {
        List<Product> testList = new ArrayList<>();
        testList.add(p1);
        testList.add(p2);
        testList.add(p3);

        productService.filterByName(testList, "Arroz");
        Assertions.assertEquals(testList.size(), 1);
    }

    @Test
    void ifNoProductFound_returnEmptyList(){
        List<Product> testList = new ArrayList<>();
        testList.add(p1);
        testList.add(p2);
        testList.add(p3);

        productService.filterByName(testList, "ajsfkajsd");
        Assertions.assertEquals(testList.size(), 0);
    }

    @Test
    void orderListByNameAsc(){
        List<Product> testList = new ArrayList<>();
        testList.add(p1);
        testList.add(p2);
        testList.add(p3);

        productService.orderByParameter(testList, "name", "asc");
        Assertions.assertEquals(testList.get(0).getName(), p3.getName());
        Assertions.assertEquals(testList.get(1).getName(), p2.getName());
        Assertions.assertEquals(testList.get(2).getName(), p1.getName());
    }

    @Test
    void orderListByPriceDesc(){
        List<Product> testList = new ArrayList<>();
        testList.add(p1);
        testList.add(p2);
        testList.add(p3);

        productService.orderByParameter(testList, "price", "desc");
        Assertions.assertEquals(testList.get(0).getName(), p2.getName());
        Assertions.assertEquals(testList.get(1).getName(), p3.getName());
        Assertions.assertEquals(testList.get(2).getName(), p1.getName());
    }
}