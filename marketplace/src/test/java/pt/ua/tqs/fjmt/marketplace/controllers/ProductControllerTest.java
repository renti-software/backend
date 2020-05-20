package pt.ua.tqs.fjmt.marketplace.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import pt.ua.tqs.fjmt.marketplace.MarketplaceApplication;
import pt.ua.tqs.fjmt.marketplace.entities.Location;
import pt.ua.tqs.fjmt.marketplace.entities.Product;
import pt.ua.tqs.fjmt.marketplace.entities.User;
import pt.ua.tqs.fjmt.marketplace.repositories.ProductRepository;

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

    @BeforeEach
    void setUp() {
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
        Product product = new Product("Car", "", "", 0.0f, null, chico);
        mockMvc.perform(postProduct("/products", product))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Inserting 3 different products, and filter with parameters should return only 1")
    public void test_product_parameters() throws Exception {
        User chico = new User("chico", "", null, "");
        Product product = new Product("Car", "Carros", "", 212, new Location("Lisboa", "Portugal"), chico);
        Product product2 = new Product("Livro", "Livros", "", 120, new Location("Aveiro", "Portugal"), chico);
        Product product3 = new Product("Car", "Carros", "", 212, new Location("Aveiro", "Portugal"), chico);
        mockMvc.perform(postProduct("/products", product)).andExpect(status().isOk());
        mockMvc.perform(postProduct("/products", product2)).andExpect(status().isOk());
        mockMvc.perform(postProduct("/products", product3)).andExpect(status().isOk());
        mockMvc.perform(get("/products/parameters?location=Lisboa&category=Carros&minPrice=200&maxPrice=300"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    private static MockHttpServletRequestBuilder postProduct (String url, Product p) {
        return post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(p));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}