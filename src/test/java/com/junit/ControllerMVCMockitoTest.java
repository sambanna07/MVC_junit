package com.junit;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junit.controllers.UserController;
import com.junit.models.User;
import com.junit.services.UserService;

// Add comments explaining the purpose of the imports

@ContextConfiguration
@AutoConfigureMockMvc
@ComponentScan(basePackages = "com.junit")
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = {ControllerMVCMockitoTest.class})
public class ControllerMVCMockitoTest {

    // Predefined for mocking MVC
    @Autowired
    MockMvc mockMvc;

    // Mock our service
    @Mock
    UserService userService;

    // We inject our mock in the controller
    @InjectMocks
    UserController userController;

    List<User> listUser;
    User user;

    // This is setup for MVC mocking
    @BeforeEach
    public void setUp() {
        // Create mock MVC object
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    // Test for getting all users
    @Test
    @Order(1)
    public void test_GetAllUser() throws Exception {
        // Define test data
        List<User> listUser = new ArrayList<>();
        listUser.add(new User(1L, "sam", 24L));
        listUser.add(new User(2L, "ankush", 21L));
        listUser.add(new User(3L, "priya", 22L));
        listUser.add(new User(4L, "roshan", 20L));
        when(userService.getAll()).thenReturn(listUser);

        // Perform the GET request and validate the response
        this.mockMvc
                .perform(get("/user/getAll"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(4)))
                .andExpectAll(
                        jsonPath("$[0].id").value(1),
                        jsonPath("$[0].name").value("sam"),
                        jsonPath("$[0].age").value(24),
                        jsonPath("$[1].id").value(2),
                        jsonPath("$[1].name").value("ankush"),
                        jsonPath("$[1].age").value(21),
                        jsonPath("$[2].id").value(3),
                        jsonPath("$[2].name").value("priya"),
                        jsonPath("$[2].age").value(22),
                        jsonPath("$[3].id").value(4),
                        jsonPath("$[3].name").value("roshan"),
                        jsonPath("$[3].age").value(20)
                )
                .andDo(print());
    }

    // Test for getting a user by ID
    @Test
    @Order(2)
    public void test_GetUserById() throws Exception {
        // Define test data
        user = new User(3L, "priya", 22L);
        String name = "priya";

        when(userService.getUserByName(name)).thenReturn(user);

        // Perform the GET request with a request parameter and validate the response
        this.mockMvc
                .perform(get("/user/get/name")
                        .param("name", "priya")
                )
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(3),
                        jsonPath("$.name").value("priya"),
                        jsonPath("$.age").value(22)
                )
                .andDo(print());
    }

    // Test for getting a user by name
    @Test
    @Order(3)
    public void test_GetUserByName() throws Exception {
        // Define test data
        user = new User(2L, "ankush", 21L);
        Long userId = 2L;

        when(userService.getUserById(userId)).thenReturn(user);

        // Perform the GET request with a path variable and validate the response
        this.mockMvc
                .perform(get("/user/get/{id}", userId))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(2),
                        jsonPath("$.name").value("ankush"),
                        jsonPath("$.age").value(21)
                )
                .andDo(print());
    }

    // Test for adding a user
    @Test
    @Order(4)
    public void test_addUser() throws Exception {
        // Define test data
        user = new User(11L, "raj", 23L);
        when(userService.saveUser(user)).thenReturn(user);

        // Perform the POST request with JSON body and validate the response
        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(user);

        this.mockMvc
                .perform(post("/user/add")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    // Test for updating a user
    @Test
    @Order(5)
    public void test_updateUser() throws Exception {
        // Define test data
        user = new User(11L, "raja", 23L);
        long id = 11L;

        when(userService.updateUser(user)).thenReturn(user);

        // Perform the PUT request with JSON body and path variable and validate the response
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(user);

        this.mockMvc.perform(put("/user/update/{id}", id)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    // Test for deleting a user by ID
    @Test
    @Order(6)
    public void test_deleteUserById() throws Exception {
        // Define test data
        user = new User(17L, "rahul", 21L);
        Long id = 17L;

        // Perform the DELETE request with a path variable and validate the response
        this.mockMvc
                .perform(delete("/user/delete/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
