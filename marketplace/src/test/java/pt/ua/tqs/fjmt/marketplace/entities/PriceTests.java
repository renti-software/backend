package pt.ua.tqs.fjmt.marketplace.entities;

import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PriceTests {

    Price price;

    @BeforeEach
    void setup() {
        price = new Price();
    }

    @Test
    @DisplayName("Should allow creation of simple price")
    public void whenSimplePrice_thenReturnOk() {
        price = new Price(100D);
        Assertions.assertEquals(100D, price.getPrice());
    }

    @Test
    @DisplayName("Should allow creation of several prices")
    public void whenSeveralPrices_thenReturnOk() {
        HashMap<Integer, Double> prices = new HashMap<Integer, Double>() {
            {
                put(0, 10D);
                put(10, 8D);
                put(20, 5D);
            }
        };

        Assertions.assertDoesNotThrow(() -> new Price(prices));
    }

    @Test
    @DisplayName("GetPrices should return prices map")
    public void whenGetPrices_thenReturnPricesMap() {
        HashMap<Integer, Double> prices = new HashMap<Integer, Double>() {
            {
                put(0, 10D);
                put(10, 8D);
                put(20, 5D);
            }
        };

        price = new Price(prices);

        Assertions.assertEquals(prices, price.getPriceMap());
    }

    @Test
    @DisplayName("GetPrice should return previously inserted price")
    public void whenGetPrice_thenReturnKnownPrice() {
        HashMap<Integer, Double> prices = new HashMap<Integer, Double>() {
            {
                put(0, 10D);
                put(10, 8D);
                put(20, 5D);
            }
        };

        price = new Price(prices);

        Assertions.assertEquals(8D, price.getPrice(10));
    }

    @Test
    @DisplayName("GetPrice() should return default price")
    public void whenGetPrice_thenReturnDefaultPrice() {
        price = new Price(10D);

        Assertions.assertEquals(10D, price.getPrice());
    }
}