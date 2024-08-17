package com.friendlyblood.api.controllers;

import com.friendlyblood.api.dtos.UserRequestDTO;
import com.friendlyblood.api.dtos.UserResponseDTO;
import com.friendlyblood.api.domain.models.User;
import com.friendlyblood.api.domain.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDTO userRequestBody) {
        User user = new User(userRequestBody);

        User savedUser = this.userService.saveUser(user);

        UserResponseDTO userResponseBody = new UserResponseDTO(savedUser.getId());
        return new ResponseEntity<>(userResponseBody, HttpStatus.CREATED);
    }
}
