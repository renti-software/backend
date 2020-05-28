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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import pt.ua.tqs.fjmt.marketplace.entities.Authenticator;
import pt.ua.tqs.fjmt.marketplace.entities.LoginForm;
import pt.ua.tqs.fjmt.marketplace.entities.User;
import pt.ua.tqs.fjmt.marketplace.repositories.AuthenticatorRepository;
import pt.ua.tqs.fjmt.marketplace.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class AuthenticatorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticatorRepository authenticatorRepository;
    
    @MockBean
    private UserRepository userRepository;

    @Autowired
    ApplicationContext context;

    @BeforeEach
    void setup() {
        authenticatorRepository.deleteAll();
        authenticatorRepository.flush();
        userRepository.deleteAll();
        userRepository.flush();
    }

    @Test
    @DisplayName("Good login should return 200")
    public void whenCorrectLogin_thenReturn200() throws Exception {
        User chico = new User("chico", "chico@mail.com", null, "ola");
        chico.setId(1L);
        ArrayList<User> users = new ArrayList<>();
        users.add(chico);

        Mockito.when(userRepository.findByEmail("chico@mail.com")).thenReturn(users);

        LoginForm loginForm = new LoginForm("chico@mail.com", "ola");

        Authenticator auth = new Authenticator(chico);
        Mockito.when(authenticatorRepository.save(Mockito.any(Authenticator.class))).thenReturn(auth);

        mockMvc.perform(postLoginForm("/login", loginForm))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Bad login should return 403")
    public void whenWrongLogin_thenReturn403() throws Exception {
        User chico = new User("chico", "chico@mail.com", null, "ola");
        chico.setId(1L);
        ArrayList<User> users = new ArrayList<>();
        users.add(chico);

        Mockito.when(userRepository.findByEmail("chico@mail.com")).thenReturn(users);

        LoginForm loginForm = new LoginForm("chico@mail.com", "olaola");

        Authenticator auth = new Authenticator(chico);
        Mockito.when(authenticatorRepository.save(Mockito.any(Authenticator.class))).thenReturn(auth);

        mockMvc.perform(postLoginForm("/login", loginForm))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    private static MockHttpServletRequestBuilder postLoginForm (String url, LoginForm l) {
        return post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(l));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}