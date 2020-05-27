package pt.ua.tqs.fjmt.marketplace.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product p1;
    Product p2;
    Product p3;
    Product p4;
    List<Product> productList;

    @BeforeEach
    void setUp() {
        productList = new ArrayList<>();

        p1 = new Product("Car", "", "", 10, "", null, null);
        p2 = new Product("Water", "", "", 200.25, "", null, null);
        p3 = new Product("Eagle", "", "", 200.24, "", null, null);
        p4 = new Product("Bus", "", "", 1.0, "", null, null);

        productList.add(p1);
        productList.add(p2);
        productList.add(p3);
        productList.add(p4);
    }

    @Test
    void orderListByNameAsc(){
        Collections.sort(productList, Product.ProdNameComparatorAsc);
        Assertions.assertEquals(productList.get(0), p4);
        Assertions.assertEquals(productList.get(1), p1);
        Assertions.assertEquals(productList.get(2), p3);
        Assertions.assertEquals(productList.get(3), p2);
    }

    @Test
    void orderListByNameDesc(){
        Collections.sort(productList, Product.ProdNameComparatorDesc);
        Assertions.assertEquals(productList.get(0), p2);
        Assertions.assertEquals(productList.get(1), p3);
        Assertions.assertEquals(productList.get(2), p1);
        Assertions.assertEquals(productList.get(3), p4);
    }

    @Test
    void orderListByPriceAsc(){
        Collections.sort(productList, Product.ProdPriceComparatorAsc);
        Assertions.assertEquals(productList.get(0), p4);
        Assertions.assertEquals(productList.get(1), p1);
        Assertions.assertEquals(productList.get(2), p3);
        Assertions.assertEquals(productList.get(3), p2);
    }

    @Test
    void orderListByPriceDesc(){
        Collections.sort(productList, Product.ProdPriceComparatorDesc);
        Assertions.assertEquals(productList.get(0), p2);
        Assertions.assertEquals(productList.get(1), p3);
        Assertions.assertEquals(productList.get(2), p1);
        Assertions.assertEquals(productList.get(3), p4);
    }


}