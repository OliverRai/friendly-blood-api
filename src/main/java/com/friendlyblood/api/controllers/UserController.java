package com.friendlyblood.api.controllers;

import com.friendlyblood.api.dtos.UserRequestDTO;
import com.friendlyblood.api.dtos.UserResponseDTO;
import com.friendlyblood.api.models.User;
import com.friendlyblood.api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDTO userRequestBody) {
        try{
            User user = new User(userRequestBody);

            User savedUser = this.userService.saveUser(user);

            UserResponseDTO userResponseBody = new UserResponseDTO(savedUser.getId());
            return new ResponseEntity<>(userResponseBody, HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>("Erro ao salvar o usuário: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}