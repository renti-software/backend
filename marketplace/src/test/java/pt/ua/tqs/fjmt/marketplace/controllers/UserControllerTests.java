package pt.ua.tqs.fjmt.marketplace.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import pt.ua.tqs.fjmt.marketplace.entities.User;
import pt.ua.tqs.fjmt.marketplace.repositories.UserRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository repository;

    @Test
    @DisplayName("Test connection")
    public void test_connection() throws Exception {
        mockMvc.perform(get("/users/")).andExpect(status().isOk());
    }

    @Test 
    @DisplayName("After inserting user API should return it")
    public void whenUserInserted_thenReturnsUser() {

    }

    @Test
    @DisplayName("Should return 404 when user not found")
    public void whenUserDoesNotExist_thenReturnsNotFound() {

    }

    @Test
    @DisplayName("Inserting user should return 200")
    public void whenCorrectInsertion_thenReturnsOk() {

    }

    @Test
    @DisplayName("Inserting bad user should return 400")
    public void whenIncorrectInsertion_thenReturnsBadRequest() {

    }

}