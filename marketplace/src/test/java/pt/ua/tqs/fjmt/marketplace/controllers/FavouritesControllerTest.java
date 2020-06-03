package pt.ua.tqs.fjmt.marketplace.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import pt.ua.tqs.fjmt.marketplace.MarketplaceApplication;
import pt.ua.tqs.fjmt.marketplace.entities.Favourites;
import pt.ua.tqs.fjmt.marketplace.entities.Product;
import pt.ua.tqs.fjmt.marketplace.entities.User;
import pt.ua.tqs.fjmt.marketplace.repositories.FavouritesRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MarketplaceApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class FavouritesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavouritesRepository favouritesRepository;

    @Autowired
    ApplicationContext context;

    @BeforeEach
    void setUp() {
        favouritesRepository.deleteAll();
        favouritesRepository.flush();
    }

    @Test
    @DisplayName("Test connection")
    public void test_connection() throws Exception {
        mockMvc.perform(get("/favourites/1")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Inserting Favourites should return 200")
    public void whenCorrectInsertion_thenReturnsOk() throws Exception {
        User chico = new User("chico", "", null, "");
        Product product = new Product("Car", "", "", 0.0, "", null, chico);
        Favourites f = new Favourites(chico, product);
        f.setId(1L);

        Mockito.when(favouritesRepository.save(Mockito.any(Favourites.class))).thenReturn(f);

        mockMvc.perform(postFavourites("/favourites", f))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get list of Favourites for one user")
    public void getAllFavourites() throws Exception{
        User chico = new User("chico", "", null, "");
        User chico2 = new User("chico2", "", null, "");
        chico.setId(1L);
        chico2.setId(2L);

        Product product = new Product("Car", "", "", 0.0, "", null, chico);
        Product product2 = new Product("Car2", "", "", 0.0, "", null, chico);
        Product product3 = new Product("Car3", "", "", 0.0, "", null, chico);
        Product product4 = new Product("Car4", "", "", 0.0, "", null, chico);

        Favourites f1 = new Favourites(chico, product);
        f1.setId(1L);
        Favourites f2 = new Favourites(chico, product2);
        f2.setId(2L);
        Favourites f3 = new Favourites(chico, product3);
        f3.setId(3L);
        Favourites f4 = new Favourites(chico2, product2);
        f4.setId(4L);
        Favourites f5 = new Favourites(chico2, product4);
        f5.setId(5L);

        List<Favourites> favourites = new ArrayList<>();
        favourites.add(f1);
        favourites.add(f2);
        favourites.add(f3);
        favourites.add(f4);
        favourites.add(f5);

        Mockito.when(favouritesRepository.save(Mockito.any(Favourites.class))).thenReturn(f1);
        Mockito.when(favouritesRepository.findAll()).thenReturn(favourites);

        FavouritesRepository favouritesRepositoryFromContext = context.getBean(FavouritesRepository.class);
        favouritesRepositoryFromContext.save(f1);
        favouritesRepositoryFromContext.save(f2);
        favouritesRepositoryFromContext.save(f3);
        favouritesRepositoryFromContext.save(f4);
        favouritesRepositoryFromContext.save(f5);
        favouritesRepositoryFromContext.flush();

        mockMvc.perform(get("/favourites"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    @DisplayName("Get list of Favourites for one user")
    public void getListForOneUser() throws Exception{
        User chico = new User("chico", "", null, "");
        User chico2 = new User("chico2", "", null, "");
        chico.setId(1L);
        chico2.setId(2L);

        Product product = new Product("Car", "", "", 0.0, "", null, chico);
        Product product2 = new Product("Car2", "", "", 0.0, "", null, chico);
        Product product3 = new Product("Car3", "", "", 0.0, "", null, chico);
        Product product4 = new Product("Car4", "", "", 0.0, "", null, chico);

        Favourites f1 = new Favourites(chico, product);
        f1.setId(1L);
        Favourites f2 = new Favourites(chico, product2);
        f2.setId(2L);
        Favourites f3 = new Favourites(chico, product3);
        f3.setId(3L);
        Favourites f4 = new Favourites(chico2, product2);
        f4.setId(4L);
        Favourites f5 = new Favourites(chico2, product4);
        f5.setId(5L);

        List<Favourites> favourites = new ArrayList<>();
        favourites.add(f1);
        favourites.add(f2);
        favourites.add(f3);
        favourites.add(f4);
        favourites.add(f5);

        Mockito.when(favouritesRepository.save(Mockito.any(Favourites.class))).thenReturn(f1);
        Mockito.when(favouritesRepository.findAll()).thenReturn(favourites);
        Mockito.when(favouritesRepository.findByUserId(chico2.getId())).thenReturn(List.of(f4, f5));

        FavouritesRepository favouritesRepositoryFromContext = context.getBean(FavouritesRepository.class);
        favouritesRepositoryFromContext.save(f1);
        favouritesRepositoryFromContext.save(f2);
        favouritesRepositoryFromContext.save(f3);
        favouritesRepositoryFromContext.save(f4);
        favouritesRepositoryFromContext.save(f5);
        favouritesRepositoryFromContext.flush();

        mockMvc.perform(get("/favourites/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].product.name", is(product2.getName())))
                .andExpect(jsonPath("$[1].product.name", is(product4.getName())))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("If no favourites are found should return empty list")
    public void getEmptyList() throws Exception{
        User chico = new User("chico", "", null, "");
        User chico2 = new User("chico2", "", null, "");
        chico.setId(1L);
        chico2.setId(2L);

        Product product = new Product("Car", "", "", 0.0, "", null, chico);
        Product product2 = new Product("Car2", "", "", 0.0, "", null, chico);
        Product product3 = new Product("Car3", "", "", 0.0, "", null, chico);
        Product product4 = new Product("Car4", "", "", 0.0, "", null, chico);

        Favourites f1 = new Favourites(chico, product);
        f1.setId(1L);
        Favourites f2 = new Favourites(chico, product2);
        f2.setId(2L);
        Favourites f3 = new Favourites(chico, product3);
        f3.setId(3L);
        Favourites f4 = new Favourites(chico2, product2);
        f4.setId(4L);
        Favourites f5 = new Favourites(chico2, product4);
        f5.setId(5L);

        List<Favourites> favourites = new ArrayList<>();
        favourites.add(f1);
        favourites.add(f2);
        favourites.add(f3);
        favourites.add(f4);
        favourites.add(f5);

        System.out.println(f1.toString());

        Mockito.when(favouritesRepository.save(Mockito.any(Favourites.class))).thenReturn(f1);
        Mockito.when(favouritesRepository.findAll()).thenReturn(favourites);

        FavouritesRepository favouritesRepositoryFromContext = context.getBean(FavouritesRepository.class);
        favouritesRepositoryFromContext.save(f1);
        favouritesRepositoryFromContext.save(f2);
        favouritesRepositoryFromContext.save(f3);
        favouritesRepositoryFromContext.save(f4);
        favouritesRepositoryFromContext.save(f5);
        favouritesRepositoryFromContext.flush();

        mockMvc.perform(get("/favourites/3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    private static MockHttpServletRequestBuilder postFavourites (String url, Favourites p) {
        return post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(p).getBytes())
                .accept(MediaType.APPLICATION_JSON);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}