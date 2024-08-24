package com.friendlyblood.api.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.friendlyblood.api.ApiApplication;
import com.friendlyblood.api.domain.models.User;
import com.friendlyblood.api.dtos.User.LoginRequestDTO;
import com.friendlyblood.api.dtos.User.UserRequestDTO;
import com.friendlyblood.api.dtos.User.UserResponseDTO;
import com.friendlyblood.api.faker.UserFaker;

@SpringBootTest(classes = ApiApplication.class)
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void createUserShouldReturnCreatedWhenValidRequest() throws Exception {
    UserRequestDTO userRequest = UserFaker.getUserRequestDTOFaker(); // Assume UserFaker creates valid UserRequestDTO

    String uri = "/user";
    mockMvc.perform(post(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userRequest)))
        .andExpect(status().isCreated());
  }

  @Test
  public void createUserShouldReturnFoundWhenEmailExists() throws Exception {
    UserRequestDTO userRequest = UserFaker.getUserRequestDTOFaker(); // Assume UserFaker creates valid UserRequestDTO

    // Create the first user
    mockMvc.perform(post("/user")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userRequest)))
        .andExpect(status().isCreated());

    // Try to create a user with the same email
    mockMvc.perform(post("/user")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userRequest)))
        .andExpect(status().isFound())
        .andExpect(content().string("Email já cadastrado"));
  }

  @Test
  public void getUserByIdShouldReturnOkWhenUserExists() throws Exception {
    UserRequestDTO userRequest = UserFaker.getUserRequestDTOFaker(); // Assume UserFaker creates valid UserRequestDTO

    // Create a user first
    User createdUser = objectMapper.readValue(mockMvc.perform(post("/user")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userRequest)))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString(), User.class);

    // Retrieve the user
    String uri = "/user/" + createdUser.getId();
    mockMvc.perform(get(uri))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(new UserResponseDTO(createdUser))));
  }

  @Test
  public void getUserByIdShouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
    String uri = "/user/" + UUID.randomUUID();
    mockMvc.perform(get(uri))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Usuário não cadastrado"));
  }

  @Test
  public void loginShouldReturnOkWhenValidCredentials() throws Exception {
    UserRequestDTO userRequest = UserFaker.getUserRequestDTOFaker(); // Assume UserFaker creates valid UserRequestDTO

    // Create a user first
    mockMvc.perform(post("/user")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userRequest)))
        .andExpect(status().isCreated());

    LoginRequestDTO loginRequest = new LoginRequestDTO(userRequest.email(), userRequest.password());

    String uri = "/user/login";
    mockMvc.perform(post(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isOk());
  }

  @Test
  public void loginShouldReturnNotFoundWhenInvalidCredentials() throws Exception {
    LoginRequestDTO loginRequest = new LoginRequestDTO("nonexistent@example.com", "wrongpassword");

    String uri = "/user/login";
    mockMvc.perform(post(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Usuário não cadastrado"));
  }
}
