package com.friendlyblood.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.friendlyblood.api.controllers.UserController;
import com.friendlyblood.api.dtos.UserRequestDTO;
import com.friendlyblood.api.utils.enums.BloodType;
import com.friendlyblood.api.domain.models.User;
import com.friendlyblood.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRequestDTO validUserRequest;

    @BeforeEach
    void setUp() {
        validUserRequest = new UserRequestDTO(
                "test@example.com",
                "password123",
                "John Doe",
                BloodType.O_POSITIVE,
                "123 Main St"
        );
    }

    @Test
    public void createUserShouldReturnOkWhenValidRequest() throws Exception {
        User user = new User(validUserRequest);
        user.setId(UUID.randomUUID());

        Mockito.when(userService.saveUser(Mockito.any(User.class))).thenReturn(user);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"" + user.getId() + "\"}"));
    }

    @Test
    public void createUserShouldReturnBadRequestWhenEmailIsInvalid() throws Exception {
        UserRequestDTO invalidUserRequest = new UserRequestDTO(
                "",
                "password123",
                "John Doe",
                BloodType.O_POSITIVE,
                "123 Main St"
        );

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserShouldReturnBadRequestWhenPasswordIsInvalid() throws Exception {
        UserRequestDTO invalidUserRequest = new UserRequestDTO(
                "test@example.com",
                "1",
                "John Doe",
                BloodType.O_POSITIVE,
                "123 Main St"
        );

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserShouldReturnBadRequestWhenNameIsInvalid() throws Exception {
        UserRequestDTO invalidUserRequest = new UserRequestDTO(
                "test@example.com",
                "password123",
                "",
                BloodType.O_POSITIVE,
                "123 Main St"
        );

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserShouldReturnBadRequestWhenAddressIsInvalid() throws Exception {
        UserRequestDTO invalidUserRequest = new UserRequestDTO(
                "test@example.com",
                "password123",
                "John Doe",
                BloodType.O_POSITIVE,
                ""
        );

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserRequest)))
                .andExpect(status().isBadRequest());
    }
}