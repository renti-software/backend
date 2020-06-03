package pt.ua.tqs.fjmt.marketplace.controllers;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import pt.ua.tqs.fjmt.marketplace.MarketplaceApplication;
import pt.ua.tqs.fjmt.marketplace.entities.Location;
import pt.ua.tqs.fjmt.marketplace.entities.Product;
import pt.ua.tqs.fjmt.marketplace.entities.Rental;
import pt.ua.tqs.fjmt.marketplace.entities.User;
import pt.ua.tqs.fjmt.marketplace.repositories.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MarketplaceApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class RentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalRepository rentalRepository;

    @Autowired
    ApplicationContext context;

    @BeforeEach
    void setUp() {
        rentalRepository.deleteAll();
        rentalRepository.flush();
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
        // UserRepository ur = context.getBean(UserRepository.class);
        // ur.save(chico);
        // ur.flush();

        Product product = new Product("Car", "", "", 0.0, "", null, chico);
        // ProductRepository pr = context.getBean(ProductRepository.class);
        // pr.save(product);
        // pr.flush();

        User renter = new User();
        // ur.save(renter);
        // ur.flush();

        Rental rental = new Rental(renter, product);

        Mockito.when(rentalRepository.save(Mockito.any(Rental.class))).thenReturn(rental);

        mockMvc.perform(postRental("/rentals", rental))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get request should return a JSON Object")
    public void whenGetRequest_thenReturnsJSON() throws Exception {

        User chico = new User("chico", "", null, "");
        Product product = new Product("Car", "Carros", "", 212, "", new Location("Lisboa", "Portugal"), chico);
        User renter = new User();
        Rental rental = new Rental(renter, product);
        rental.setId(1L);

        Mockito.when(rentalRepository.save(Mockito.any(Rental.class))).thenReturn(rental);
        Mockito.when(rentalRepository.findById(rental.getId())).thenReturn(Optional.of(rental));

        // mockMvc.perform(postRental("/rentals", rental)).andReturn().getResponse().getContentAsString();
        RentalRepository rentalRepositoryFromContext = context.getBean(RentalRepository.class);
        rentalRepositoryFromContext.save(rental);
        rentalRepositoryFromContext.flush();
        // System.out.println(rentalRepositoryFromContext.findAll());

        mockMvc.perform(get("/rentals/" + rental.getId()))
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
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Controller should allow to fetch nested Product")
    public void whenNestedGet_thenReturnProduct() throws Exception {
        User chico = new User("chico", "", null, "");
        Product product = new Product("Car", "Carros", "", 212, "", new Location("Lisboa", "Portugal"), chico);
        User renter = new User();
        Rental rental = new Rental(renter, product);
        rental.setId(1L);

        Mockito.when(rentalRepository.save(Mockito.any(Rental.class))).thenReturn(rental);
        Mockito.when(rentalRepository.findById(rental.getId())).thenReturn(Optional.of(rental));

        RentalRepository rentalRepositoryFromContext = context.getBean(RentalRepository.class);
        rentalRepositoryFromContext.save(rental);
        rentalRepositoryFromContext.flush();

        mockMvc.perform(get("/rentals/" + rental.getId() + "/product"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Controller should allow to fetch nested Renter")
    public void whenNestedGet_thenReturnRenter() throws Exception {
        User chico = new User("chico", "", null, "");
        Product product = new Product("Car", "Carros", "", 212, "", new Location("Lisboa", "Portugal"), chico);
        User renter = new User();
        Rental rental = new Rental(renter, product);
        rental.setId(1L);

        Mockito.when(rentalRepository.save(Mockito.any(Rental.class))).thenReturn(rental);
        Mockito.when(rentalRepository.findById(rental.getId())).thenReturn(Optional.of(rental));

        RentalRepository rentalRepositoryFromContext = context.getBean(RentalRepository.class);
        rentalRepositoryFromContext.save(rental);
        rentalRepositoryFromContext.flush();


        mockMvc.perform(get("/rentals/" + rental.getId() + "/renter"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
    
    @Test
    @DisplayName("Controller should allow to filtering by approval state")
    public void whenFilterByApproval_thenReturnRenter() throws Exception {
        User chico = new User("chico", "", null, "");
        Product product = new Product("Car", "Carros", "", 212, "", new Location("Lisboa", "Portugal"), chico);
        User renter = new User();
        Rental rental = new Rental(renter, product);

        Mockito.when(rentalRepository.save(Mockito.any(Rental.class))).thenReturn(rental);
        Mockito.when(rentalRepository.findByApproved(false)).thenReturn(List.of(rental));

        RentalRepository rentalRepositoryFromContext = context.getBean(RentalRepository.class);
        rentalRepositoryFromContext.save(rental);
        rentalRepositoryFromContext.flush();


        mockMvc.perform(get("/rentals?approved=" + "false"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Controller should allow to filtering by approval state and user")
    public void whenFilterByApprovalAndUser_thenReturnRental() throws Exception {
        User chico = new User("chico", "", null, "");
        chico.setId(1L);
        User chico2 = new User("chico", "", null, "");
        chico2.setId(2L);
        Location l = new Location("Lisboa", "Portugal");
        Product product = new Product("Car", "Carros", "", 212, "", l, chico);
        Product product2 = new Product("Car", "Carros", "", 212, "", l, chico2);
        User renter = new User();
        User renter2 = new User();
        Rental rental = new Rental(renter, product);
        Rental rental2 = new Rental(renter2, product);
        Rental rental3 = new Rental(renter2, product2);
        rental2.setApproved(true);

        Mockito.when(rentalRepository.save(Mockito.any(Rental.class))).thenReturn(rental);
        Mockito.when(rentalRepository.findByApproved(false)).thenReturn(List.of(rental, rental3));
        Mockito.when(rentalRepository.findByProductUserId(chico.getId())).thenReturn(List.of(rental, rental2));
        Mockito.when(rentalRepository.findAll()).thenReturn(List.of(rental, rental2, rental3));

        RentalRepository rentalRepositoryFromContext = context.getBean(RentalRepository.class);
        rentalRepositoryFromContext.save(rental);
        rentalRepositoryFromContext.flush();
        rentalRepositoryFromContext.save(rental2);
        rentalRepositoryFromContext.flush();
        rentalRepositoryFromContext.save(rental3);
        rentalRepositoryFromContext.flush();

        for(Rental r : rentalRepositoryFromContext.findAll())
            System.out.println(r);

        Boolean approved = false;
        Long ownerId = chico.getId();
        System.out.println("HERE");
        System.out.println("/rentals" + "?approved=" + approved.toString() + "&ownerId=" + ownerId.toString());
        mockMvc.perform(get("/rentals" + "?approved=" + approved.toString() + "&ownerId=" + ownerId.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(rental.getId())));
    }

    @Test
    @DisplayName("Controller should allow to filtering by approval state and product")
    public void whenFilterByApprovalAndProduct_thenReturnRental() throws Exception {
        User chico = new User("chico", "", null, "");
        chico.setId(1L);
        User chico2 = new User("chico", "", null, "");
        chico2.setId(2L);

        Location l = new Location("Lisboa", "Portugal");

        Product product = new Product("Car", "Carros", "", 212, "", l, chico);
        Product product2 = new Product("Car", "Carros", "", 212, "", l, chico2);
        product2.setId(2L);

        User renter = new User();
        User renter2 = new User();
        Rental rental = new Rental(renter, product);
        Rental rental2 = new Rental(renter2, product);
        Rental rental3 = new Rental(renter2, product2);
        rental2.setApproved(true);

        Mockito.when(rentalRepository.save(Mockito.any(Rental.class))).thenReturn(rental);
        Mockito.when(rentalRepository.findByApproved(false)).thenReturn(List.of(rental, rental3));
        Mockito.when(rentalRepository.findByProductId(product2.getId())).thenReturn(List.of(rental3));
        Mockito.when(rentalRepository.findAll()).thenReturn(List.of(rental, rental2, rental3));

        RentalRepository rentalRepositoryFromContext = context.getBean(RentalRepository.class);
        rentalRepositoryFromContext.save(rental);
        rentalRepositoryFromContext.flush();
        rentalRepositoryFromContext.save(rental2);
        rentalRepositoryFromContext.flush();
        rentalRepositoryFromContext.save(rental3);
        rentalRepositoryFromContext.flush();


        Boolean approved = false;
        Long productId = product2.getId();

        mockMvc.perform(get("/rentals?approved=false&productId=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(rental3.getId())));
    }
    
    @Test
    @DisplayName("Filtering by approval state should return 404 when none approved")
    public void whenFilterByApproval_thenReturn404() throws Exception {
        User chico = new User("chico", "", null, "");
        Product product = new Product("Car", "Carros", "", 212, "", new Location("Lisboa", "Portugal"), chico);
        User renter = new User();
        Rental rental = new Rental(renter, product);

        Mockito.when(rentalRepository.save(Mockito.any(Rental.class))).thenReturn(rental);
        Mockito.when(rentalRepository.findByApproved(true)).thenReturn(new ArrayList<Rental>());

        RentalRepository rentalRepositoryFromContext = context.getBean(RentalRepository.class);
        rentalRepositoryFromContext.save(rental);
        rentalRepositoryFromContext.flush();


        mockMvc.perform(get("/rentals?approved=" + "true"))
                .andDo(print())
                .andExpect(status().isNotFound());
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
