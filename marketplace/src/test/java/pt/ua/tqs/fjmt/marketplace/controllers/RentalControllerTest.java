package pt.ua.tqs.fjmt.marketplace.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import pt.ua.tqs.fjmt.marketplace.MarketplaceApplication;
import pt.ua.tqs.fjmt.marketplace.entities.Location;
import pt.ua.tqs.fjmt.marketplace.entities.Product;
import pt.ua.tqs.fjmt.marketplace.entities.Rental;
import pt.ua.tqs.fjmt.marketplace.entities.User;
import pt.ua.tqs.fjmt.marketplace.repositories.RentalRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MarketplaceApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class RentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalRepository rentalRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Test connection")
    public void test_connection() throws Exception {
        mockMvc.perform(get("/rentals")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Inserting rental should return 200")
    public void whenCorrectInsertion_thenReturnsOk() throws Exception {
        User chico = new User("chico", "", null, "");
        Product product = new Product("Car", "", "", 0.0f, null, chico);
        User renter = new User();
        Rental rental = new Rental(renter, product);

        mockMvc.perform(postRental("/rentals", rental))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get request should return a JSON Object")
    public void whenGetRequest_thenReturnsJSON() throws Exception {
        User chico = new User("chico", "", null, "");
        Product product = new Product("Car", "Carros", "", 212, new Location("Lisboa", "Portugal"), chico);
        User renter = new User();
        Rental rental = new Rental(renter, product);


        String id = mockMvc.perform(postRental("/rentals", rental)).andReturn().getResponse().getContentAsString();
        System.out.println(id);

        mockMvc.perform(get("/rentals/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Get request should return 404 when Rental not found")
    public void whenGetRequest_thenReturns404() throws Exception {
        // given 
        rentalRepository.deleteAll();

        mockMvc.perform(get("/rentals/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Controller should allow to fetch nested Product")
    public void whenNestedGet_thenReturnProduct() throws Exception {
        User chico = new User("chico", "", null, "");
        Product product = new Product("Car", "Carros", "", 212, new Location("Lisboa", "Portugal"), chico);
        User renter = new User();
        Rental rental = new Rental(renter, product);


        String id = mockMvc.perform(postRental("/rentals", rental)).andReturn().getResponse().getContentAsString();
        System.out.println(id);

        mockMvc.perform(get("/rentals/" + id + "/product"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Controller should allow to fetch nested Renter")
    public void whenNestedGet_thenReturnRenter() throws Exception {
        User chico = new User("chico", "", null, "");
        Product product = new Product("Car", "Carros", "", 212, new Location("Lisboa", "Portugal"), chico);
        User renter = new User();
        Rental rental = new Rental(renter, product);


        String id = mockMvc.perform(postRental("/rentals", rental)).andReturn().getResponse().getContentAsString();
        System.out.println(id);

        mockMvc.perform(get("/rentals/" + id + "/renter"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    private static MockHttpServletRequestBuilder postRental (String url, Rental r) {
        return post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(r));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
