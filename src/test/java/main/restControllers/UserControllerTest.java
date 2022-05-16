package main.restControllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.util.AbstractRestControllerTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Value;

import static main.util.LogInUtils.getTokenForLogin;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *  UserControllerTest -  methods coverage in AuthController and InController
 *
 *  Created test user, use all methods and delete in class AdminControllerTest
 *
 *  First one Run test cases in this class (UserControllerTest),
 *  because this class create test user and delete him in AdminControllerTest
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest extends AbstractRestControllerTest {

    @Value("${test.junit.username}")
    private String username;

    @Value("${test.junit.password}")
    private String password;

    private static String token;

    private static String id;

    private final String URL = "/in";


    @Test
    @Order(1)
    public void registerUserTest() throws Exception {
        ResultActions mvcResult = getMockMvc().perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                " \"username\": \"" + username + "\",\n" +
                                " \"email\": \"test@test.ru\",\n" +
                                " \"password\": \"" + password + "\",\n" +
                                " \"fullName\": \"Angela Santos\",\n" +
                                " \"address\": \"Istanbul, Bayrampasha\",\n" +
                                " \"phone\": \"+905454919654\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void authenticateUserTest() throws Exception {
        token = getTokenForLogin(username, password, getMockMvc());

        assertNotNull(token);
    }


    @Test
    @Order(3)
    public void addPetTest() throws Exception {
        ResultActions resultActions = getMockMvc().perform(post(URL + "/user/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content("{\n" +
                                " \"breed\": \"Haski\",\n" +
                                " \"name\": \"Tom\",\n" +
                                " \"gender\": \"male\",\n" +
                                " \"age\": \"" + 1 + "\",\n" +
                                " \"description\": \"Tom is sic\"\n" +
                                "}"))
                .andExpect(status().isCreated());

        JsonNode responseObject = new ObjectMapper()
                .readTree(resultActions.andReturn().getResponse().getContentAsString());

        id = responseObject.get("id").asText();
    }


    @Test
    @Order(4)
    public void getPetByNameTest() throws Exception {
        getMockMvc().perform(get(URL + "/user/pet/?name=Tom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    public void getPetsByOwnerNameTest() throws Exception {
        getMockMvc().perform(get(URL + "/user/pets/?name=Angela Santos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    public void updateOwnerTest() throws Exception {
        getMockMvc().perform(put(URL + "/user/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content("{\n" +
                                " \"fullName\": \"Test Test\",\n" +
                                " \"address\": \"United Kingdom\",\n" +
                                " \"phone\": \"+905645946352\",\n" +
                                " \"email\": \"test@test.ru\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(7)
    public void updatePetTest() throws Exception {
        getMockMvc().perform(put(URL + "/user/pet/?id=" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content("{\n" +
                                " \"breed\": \"Haski\",\n" +
                                " \"name\": \"Tom\",\n" +
                                " \"gender\": \"female\",\n" +
                                " \"age\": \"" + 1 + "\",\n" +
                                " \"description\": \"Tom is live\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }
}
