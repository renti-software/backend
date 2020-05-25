package pt.ua.tqs.fjmt.marketplace.entities;

import java.util.HashMap;

public class Price {

    HashMap<Integer, Double> priceMap;

    public Price(Double price) {
        this.priceMap = new HashMap<>();
        this.priceMap.put(0, price);
    }

    public Price(HashMap<Integer, Double> priceMap) {
        this.priceMap = priceMap;
    }

    public Price () {
    }

    public Double getPrice() {
        return priceMap.get(0);
    }

    public Double getPrice(int numDays) {
        return priceMap.get(numDays);
    }

    public HashMap<Integer, Double> getPriceMap() {
        return priceMap;
    }
    
}