package pt.ua.tqs.fjmt.marketplace.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pt.ua.tqs.fjmt.marketplace.MarketplaceApplication;
import pt.ua.tqs.fjmt.marketplace.entities.Location;
import pt.ua.tqs.fjmt.marketplace.entities.Product;
import pt.ua.tqs.fjmt.marketplace.entities.User;
import pt.ua.tqs.fjmt.marketplace.repositories.ProductRepository;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MarketplaceApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    ApplicationContext context;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        productRepository.flush();
    }

    @Test
    @DisplayName("Test connection")
    public void test_connection() throws Exception {
        mockMvc.perform(get("/products")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Inserting product should return 200")
    public void whenCorrectInsertion_thenReturnsOk() throws Exception {
        User chico = new User("chico", "", null, "");
        Product product = new Product("Car", "", "", 0.0, "", null, chico);

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        mockMvc.perform(postProduct("/products", product))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Get request should return a JSON Object for id path")
    public void whenGetRequestID_thenReturnsJSON() throws Exception {
        User chico = new User("chico", "", null, "");
        Product product = new Product("Car", "", "", 0.0, "", null, chico);
        product.setId(1L);

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Mockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        ProductRepository productRepositoryFromContext = context.getBean(ProductRepository.class);
        productRepositoryFromContext.save(product);
        productRepositoryFromContext.flush();

        System.out.println(productRepositoryFromContext.findAll());

        mockMvc.perform(get("/products/" + product.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(product.getName())));
    }

    @Test
    @DisplayName("Get request should return 404 when Product not found with id")
    public void whenGetRequest_thenReturns404() throws Exception {
        productRepository.deleteAll();

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get request should return empty List when Product not found")
    public void whenGetRequest_thenReturnsEmptyList() throws Exception {
        productRepository.deleteAll();

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    @DisplayName("Get request should return a JSON Object for main path")
    public void whenGetRequestMain_thenReturnsJSON() throws Exception {
        User chico = new User("chico", "", null, "");
        Product product = new Product("Car", "", "", 0.0, "", null, chico);
        product.setId(1L);

        List<Product> products = new ArrayList<>();
        products.add(product);

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Mockito.when(productRepository.findAll()).thenReturn(products);

        ProductRepository productRepositoryFromContext = context.getBean(ProductRepository.class);
        productRepositoryFromContext.save(product);
        productRepositoryFromContext.flush();

        System.out.println(productRepositoryFromContext.findAll());

        mockMvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is(product.getName())));
    }

    @Test
    @DisplayName("Get request with parameters (name, Category) should return one product with those parameters")
    public void whenGetRequestParameters_thenReturnsProductWithCorrectParameters() throws Exception {
        User chico = new User("chico", "", null, "");
        Product product = new Product("Car", "Veiculos", "", 0.0, "", null, chico);
        product.setId(1L);
        Product product2 = new Product("Bus", "Veiculos", "", 0.0, "", null, chico);
        product2.setId(2L);
        Product product3 = new Product("Arroz", "Comida", "", 0.0, "", null, chico);
        product3.setId(3L);

        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product2);
        products.add(product3);

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Mockito.when(productRepository.findAll()).thenReturn(products);

        ProductRepository productRepositoryFromContext = context.getBean(ProductRepository.class);
        productRepositoryFromContext.save(product);
        productRepositoryFromContext.save(product2);
        productRepositoryFromContext.save(product3);
        productRepositoryFromContext.flush();

        System.out.println(productRepositoryFromContext.findAll());

        mockMvc.perform(get("/products?name=Car&category=Veiculos"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is(product.getName())))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Get request with category should return all products in that category")
    public void whenGetRequestCategory_thenReturnsProductsInThatCategory() throws Exception {
        User chico = new User("chico", "", null, "");
        Product product = new Product("Car", "Veiculos", "", 0.0, "", null, chico);
        product.setId(1L);
        Product product2 = new Product("Bus", "Veiculos", "", 0.0, "", null, chico);
        product2.setId(2L);
        Product product3 = new Product("Arroz", "Comida", "", 0.0, "", null, chico);
        product3.setId(3L);

        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product2);
        products.add(product3);

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Mockito.when(productRepository.findAll()).thenReturn(products);

        ProductRepository productRepositoryFromContext = context.getBean(ProductRepository.class);
        productRepositoryFromContext.save(product);
        productRepositoryFromContext.save(product2);
        productRepositoryFromContext.save(product3);
        productRepositoryFromContext.flush();

        System.out.println(productRepositoryFromContext.findAll());

        mockMvc.perform(get("/products?category=Veiculos"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("Get request with price range should return all products in that price range")
    public void whenGetRequestPriceRange_thenReturnsProductsInThatPriceRange() throws Exception {
        User chico = new User("chico", "", null, "");
        Product product = new Product("Car", "Veiculos", "", 1.70, "", null, chico);
        product.setId(1L);
        Product product2 = new Product("Bus", "Veiculos", "", 12, "", null, chico);
        product2.setId(2L);
        Product product3 = new Product("Arroz", "Comida", "", 120, "", null, chico);
        product3.setId(3L);
        Product product4 = new Product("Bomba", "Comida", "", 200.45, "", null, chico);
        product4.setId(4L);

        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product2);
        products.add(product3);
        products.add(product4);

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Mockito.when(productRepository.findAll()).thenReturn(products);

        ProductRepository productRepositoryFromContext = context.getBean(ProductRepository.class);
        productRepositoryFromContext.save(product);
        productRepositoryFromContext.save(product2);
        productRepositoryFromContext.save(product3);
        productRepositoryFromContext.save(product4);
        productRepositoryFromContext.flush();

        System.out.println(productRepositoryFromContext.findAll());

        mockMvc.perform(get("/products?minPrice=100.30&maxPrice=300"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("Get request with Location should return all products in that location")
    public void whenGetRequestLocation_thenReturnsProductsInThatLocation() throws Exception {
        User chico = new User("chico", "", null, "");
        Location aveiro = new Location("Aveiro", "Portugal");
        Location porto = new Location("Porto", "Portugal");
        Product product = new Product("Car", "Veiculos", "", 1.70, "", aveiro, chico);
        product.setId(1L);
        Product product2 = new Product("Bus", "Veiculos", "", 12, "", aveiro, chico);
        product2.setId(2L);
        Product product3 = new Product("Arroz", "Comida", "", 120, "", porto, chico);
        product3.setId(3L);
        Product product4 = new Product("Bomba", "Comida", "", 200.45, "", porto, chico);
        product4.setId(4L);

        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product2);
        products.add(product3);
        products.add(product4);

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Mockito.when(productRepository.findAll()).thenReturn(products);

        ProductRepository productRepositoryFromContext = context.getBean(ProductRepository.class);
        productRepositoryFromContext.save(product);
        productRepositoryFromContext.save(product2);
        productRepositoryFromContext.save(product3);
        productRepositoryFromContext.save(product4);
        productRepositoryFromContext.flush();

        System.out.println(productRepositoryFromContext.findAll());

        mockMvc.perform(get("/products?location=Aveiro"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("Get request with all parameters should return all products that obey those parameters")
    public void whenGetRequestAllParameters_thenReturnsProductsThatObeyParameters() throws Exception {
        User chico = new User("chico", "", null, "");
        Location aveiro = new Location("Aveiro", "Portugal");
        Location porto = new Location("Porto", "Portugal");
        Product product = new Product("Car", "Veiculos", "", 1.70, "", aveiro, chico);
        product.setId(1L);
        Product product2 = new Product("Car2", "Veiculos", "", 12, "", porto, chico);
        product2.setId(2L);
        Product product3 = new Product("Arroz", "Comida", "", 120, "", aveiro, chico);
        product3.setId(3L);
        Product product4 = new Product("Bomba", "Comida", "", 200.45, "", porto, chico);
        product4.setId(4L);

        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product2);
        products.add(product3);
        products.add(product4);

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Mockito.when(productRepository.findAll()).thenReturn(products);

        ProductRepository productRepositoryFromContext = context.getBean(ProductRepository.class);
        productRepositoryFromContext.save(product);
        productRepositoryFromContext.save(product2);
        productRepositoryFromContext.save(product3);
        productRepositoryFromContext.save(product4);
        productRepositoryFromContext.flush();

        System.out.println(productRepositoryFromContext.findAll());

        mockMvc.perform(get("/products?name=Car&category=Veiculos&location=Aveiro&minPrice=0&maxPrice=200"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Get request with order by name asc should return all products that obey those parameters")
    public void whenGetRequestOrderNameAsc_thenReturnsProductsListOrderedLikeThat() throws Exception {
        User chico = new User("chico", "", null, "");
        Location aveiro = new Location("Aveiro", "Portugal");
        Location porto = new Location("Porto", "Portugal");
        Product product = new Product("Car", "Veiculos", "", 1.70, "", aveiro, chico);
        product.setId(1L);
        Product product2 = new Product("Car2", "Veiculos", "", 12, "", porto, chico);
        product2.setId(2L);
        Product product3 = new Product("Arroz", "Comida", "", 120, "", aveiro, chico);
        product3.setId(3L);
        Product product4 = new Product("Bomba", "Comida", "", 200.45, "", porto, chico);
        product4.setId(4L);

        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product2);
        products.add(product3);
        products.add(product4);

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Mockito.when(productRepository.findAll()).thenReturn(products);

        ProductRepository productRepositoryFromContext = context.getBean(ProductRepository.class);
        productRepositoryFromContext.save(product);
        productRepositoryFromContext.save(product2);
        productRepositoryFromContext.save(product3);
        productRepositoryFromContext.save(product4);
        productRepositoryFromContext.flush();

        System.out.println(productRepositoryFromContext.findAll());

        mockMvc.perform(get("/products?orderParameter=name&order=asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is(product3.getName())))
                .andExpect(jsonPath("$[1].name", is(product4.getName())))
                .andExpect(jsonPath("$[2].name", is(product.getName())))
                .andExpect(jsonPath("$[3].name", is(product2.getName())));
    }

    @Test
    @DisplayName("Get request with order by price desc should return all products that obey those parameters")
    public void whenGetRequestOrderPriceDesc_thenReturnsProductsListOrderedLikeThat() throws Exception {
        User chico = new User("chico", "", null, "");
        Location aveiro = new Location("Aveiro", "Portugal");
        Location porto = new Location("Porto", "Portugal");
        Product product = new Product("Car", "Veiculos", "", 1.70, "", aveiro, chico);
        product.setId(1L);
        Product product2 = new Product("Car2", "Veiculos", "", 12, "", porto, chico);
        product2.setId(2L);
        Product product3 = new Product("Arroz", "Comida", "", 120, "", aveiro, chico);
        product3.setId(3L);
        Product product4 = new Product("Bomba", "Comida", "", 200.45, "", porto, chico);
        product4.setId(4L);

        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product2);
        products.add(product3);
        products.add(product4);

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Mockito.when(productRepository.findAll()).thenReturn(products);

        ProductRepository productRepositoryFromContext = context.getBean(ProductRepository.class);
        productRepositoryFromContext.save(product);
        productRepositoryFromContext.save(product2);
        productRepositoryFromContext.save(product3);
        productRepositoryFromContext.save(product4);
        productRepositoryFromContext.flush();

        System.out.println(productRepositoryFromContext.findAll());

        mockMvc.perform(get("/products?orderParameter=price&order=desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is(product4.getName())))
                .andExpect(jsonPath("$[1].name", is(product3.getName())))
                .andExpect(jsonPath("$[2].name", is(product2.getName())))
                .andExpect(jsonPath("$[3].name", is(product.getName())));
    }

    @Test
    @DisplayName("Get request with filtering and then ordering")
    public void whenGetRequestForFilterByNameAndOrderByPriceAsc_thenReturnsProductsListFilteredAndOrderedLikeThat() throws Exception {
        User chico = new User("chico", "", null, "");
        Location aveiro = new Location("Aveiro", "Portugal");
        Location porto = new Location("Porto", "Portugal");
        Product product = new Product("Car", "Veiculos", "", 1.70, "", aveiro, chico);
        product.setId(1L);
        Product product2 = new Product("Car2", "Veiculos", "", 12, "", porto, chico);
        product2.setId(2L);
        Product product3 = new Product("Arroz", "Comida", "", 120, "", aveiro, chico);
        product3.setId(3L);
        Product product4 = new Product("Bomba", "Comida", "", 200.45, "", porto, chico);
        product4.setId(4L);

        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product2);
        products.add(product3);
        products.add(product4);

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Mockito.when(productRepository.findAll()).thenReturn(products);

        ProductRepository productRepositoryFromContext = context.getBean(ProductRepository.class);
        productRepositoryFromContext.save(product);
        productRepositoryFromContext.save(product2);
        productRepositoryFromContext.save(product3);
        productRepositoryFromContext.save(product4);
        productRepositoryFromContext.flush();

        System.out.println(productRepositoryFromContext.findAll());

        mockMvc.perform(get("/products?name=Car&orderParameter=price&order=asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is(product.getName())))
                .andExpect(jsonPath("$[1].name", is(product2.getName())));
    }

    @Test
    @DisplayName("Get for price should return the calculated price based on the number of days between dates")
    public void whenGetRequestPrice_thenReturnsCalculatedPrice() throws Exception {
        User chico = new User("chico", "", null, "");
        HashMap<Integer, Double> priceMap = new HashMap<>();
        priceMap.put(1, 20.0);
        priceMap.put(10, 18.0);
        priceMap.put(20, 15.0);

        Product product = new Product("Car", "", "", priceMap, "", null, chico);
        product.setId(1L);

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Mockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        ProductRepository productRepositoryFromContext = context.getBean(ProductRepository.class);
        productRepositoryFromContext.save(product);
        productRepositoryFromContext.flush();

        System.out.println(productRepositoryFromContext.findAll());

        mockMvc.perform(get("/products/" + product.getId() + "/price?startDate=2020-01-29&endDate=2020-02-14"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("288.0"));
    }

    @Test
    @DisplayName("Get request for list of products from one user")
    public void whenGetRequestForFilterByUserId_thenReturnsProductsListFilteredLikeThat() throws Exception {
        User chico = new User("chico", "", null, "");
        chico.setId(1L);
        User chico2 = new User("chico2", "", null, "");
        chico2.setId(2L);
        User chico3 = new User("chico3", "", null, "");
        chico3.setId(3L);

        Location aveiro = new Location("Aveiro", "Portugal");
        Location porto = new Location("Porto", "Portugal");
        Product product = new Product("Car", "Veiculos", "", 1.70, "", aveiro, chico);
        product.setId(1L);
        Product product2 = new Product("Car2", "Veiculos", "", 12, "", porto, chico);
        product2.setId(2L);
        Product product3 = new Product("Arroz", "Comida", "", 120, "", aveiro, chico2);
        product3.setId(3L);
        Product product4 = new Product("Bomba", "Comida", "", 200.45, "", porto, chico3);
        product4.setId(4L);

        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product2);
        products.add(product3);
        products.add(product4);

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Mockito.when(productRepository.findAll()).thenReturn(products);

        ProductRepository productRepositoryFromContext = context.getBean(ProductRepository.class);
        productRepositoryFromContext.save(product);
        productRepositoryFromContext.save(product2);
        productRepositoryFromContext.save(product3);
        productRepositoryFromContext.save(product4);
        productRepositoryFromContext.flush();

        System.out.println(productRepositoryFromContext.findAll());

        mockMvc.perform(get("/products?userId=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }


    private static MockHttpServletRequestBuilder postProduct (String url, Product p) {
        return post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(p));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}