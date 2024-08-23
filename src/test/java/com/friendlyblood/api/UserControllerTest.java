package com.friendlyblood.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.friendlyblood.api.config.security.SecurityConfigurations;
import com.friendlyblood.api.controllers.UserController;
import com.friendlyblood.api.domain.enums.BloodType;
import com.friendlyblood.api.domain.models.User;
import com.friendlyblood.api.domain.services.UserService;
import com.friendlyblood.api.dtos.User.UserRequestDTO;
import com.friendlyblood.api.dtos.User.UserResponseDTO;

@WebMvcTest(UserController.class)
@Import(SecurityConfigurations.class)
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
                                "123 Main St");
        }

        @Test
        public void createUserShouldReturnOkWhenValidRequest() throws Exception {
                User user = new User(validUserRequest);
                user.setId(UUID.randomUUID());

                Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user);

                mockMvc.perform(post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validUserRequest)))
                                .andExpect(status().isCreated())
                                .andExpect(content().json("{\"id\":\"" + user.getId() + "\"}"));
        }

        @Test
        public void createUserShouldReturnBadRequestWhenEmailIsInvalid() throws Exception {
                UserRequestDTO invalidUserRequest = new UserRequestDTO(
                                "",
                                "password123",
                                "John Doe",
                                BloodType.O_POSITIVE,
                                "123 Main St");

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
                                "123 Main St");

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
                                "123 Main St");

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
                                "");

                mockMvc.perform(post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidUserRequest)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        public void createUserShouldReturnFoundWhenEmailAlreadyExists() throws Exception {
                User existingUser = new User(validUserRequest);
                existingUser.setId(UUID.randomUUID());

                Mockito.when(userService.getUserByEmail(validUserRequest.email()))
                                .thenReturn(Optional.of(existingUser));

                mockMvc.perform(post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(validUserRequest)))
                                .andExpect(status().isFound())
                                .andExpect(content().string("Email já cadastrado"));
        }

        @Test
        public void getUserByIdShouldReturnOkWhenUserExists() throws Exception {
                User user = new User(validUserRequest);
                user.setId(UUID.randomUUID());

                Mockito.when(userService.getUserById(user.getId())).thenReturn(Optional.of(user));

                UserResponseDTO userResponse = new UserResponseDTO(user);

                mockMvc.perform(get("/user/{id}", user.getId()))
                                .andExpect(status().isOk())
                                .andExpect(content().json(objectMapper.writeValueAsString(userResponse)));
        }

        @Test
        public void getUserByIdShouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
                UUID nonExistentId = UUID.randomUUID();

                Mockito.when(userService.getUserById(nonExistentId)).thenReturn(Optional.empty());

                mockMvc.perform(get("/user/{id}", nonExistentId))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string("Usuário não cadastrado"));
        }

        @Test
        public void getUserByIdShouldReturnBadRequestWhenIdIsInvalid() throws Exception {
                String invalidId = "invalid-uuid";

                mockMvc.perform(get("/user/{id}", invalidId))
                                .andExpect(status().isBadRequest());
        }

        @Test
        public void createUserShouldReturnBadRequestWhenRequiredFieldsAreMissing() throws Exception {
                UserRequestDTO invalidUserRequest = new UserRequestDTO(
                                null,
                                "password123",
                                "John Doe",
                                BloodType.O_POSITIVE,
                                "123 Main St");

                mockMvc.perform(post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidUserRequest)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        public void createUserShouldReturnCreatedWhenOptionalFieldsAreProvided() throws Exception {
                UserRequestDTO userRequestWithOptionalFields = new UserRequestDTO(
                                "test@example.com",
                                "password123",
                                "John Doe",
                                BloodType.O_NEGATIVE,
                                "456 Another St");

                User user = new User(userRequestWithOptionalFields);
                user.setId(UUID.randomUUID());

                Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user);

                mockMvc.perform(post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userRequestWithOptionalFields)))
                                .andExpect(status().isCreated())
                                .andExpect(content().json("{\"id\":\"" + user.getId() + "\"}"));
        }
}