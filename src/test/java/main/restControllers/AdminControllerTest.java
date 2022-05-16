package main.restControllers;

import main.DAO.OwnerDAO;
import main.entities.Pet;
import main.entities.User;
import main.util.AbstractRestControllerTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static main.util.LogInUtils.getTokenForLogin;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *  AdminControllerTest - methods coverage in AuthController and AdminController
 *  Testing just authenticateUser() method from AuthController
 *
 *  First one Run test cases in UserControllerTest class,
 *  because this class create test user and delete him in AdminControllerTest
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminControllerTest extends AbstractRestControllerTest {

    private static String token;

    private static final String URL = "/admin";

    private static final String USERNAME  = "admin";

    private static final String PASSWORD = "Islam1995";

    private static long ownerId;

    private static long petId;

    @Value("${test.junit.username}")
    private String username;


    @Autowired
    private OwnerDAO ownerDAO;

    @Test
    @Order(1)
    @Transactional
    public void setUpBefore(){
        List<User> list = ownerDAO.getOwners();
        for (User user : list){
            if (user.getUsername().contains(username)){
                ownerId = user.getId();
                petId = user.getPetList().get(0).getId();

            }
        }

    }

    @Test
    @Order(2)
    public void authenticateAdminTest() throws Exception {
        token = getTokenForLogin(USERNAME, PASSWORD, getMockMvc());
        assertNotNull(token);
    }

    @Test
    @Order(3)
    public void getOwnersTest() throws Exception {
        getMockMvc().perform(get(URL + "/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void getPetsTest() throws Exception {
        getMockMvc().perform(get(URL + "/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    public void addPetTest() throws Exception {
        getMockMvc().perform(post(URL + "/pet/?id=" + ownerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content("{\n" +
                                " \"breed\": \"Terier\",\n" +
                                " \"name\": \"Jerry\",\n" +
                                " \"gender\": \"male\",\n" +
                                " \"age\": \"" + 2 + "\",\n" +
                                " \"description\": \"Jerry is sic\"\n" +
                                "}"))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(6)
    public void deletePetByIdTest() throws Exception {
        getMockMvc().perform(delete(URL + "/pet/?id=" + petId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @Order(7)
    public void deleteOwnerByIdTest() throws Exception {
        getMockMvc().perform(delete(URL + "/owner/?id=" + ownerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

}
