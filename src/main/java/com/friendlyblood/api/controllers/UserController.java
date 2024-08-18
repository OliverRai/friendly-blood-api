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

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDTO userRequestBody) {
        User user = new User(userRequestBody);

        if (this.userService.getUserByEmail(user.getEmail()).isPresent()){
            return new ResponseEntity<>("Email já cadastrado", HttpStatus.FOUND);
        }

        User savedUser = this.userService.saveUser(user);

        UserResponseDTO userResponseBody = new UserResponseDTO(savedUser.getId());
        return new ResponseEntity<>(userResponseBody, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserByEmail(@PathVariable UUID id){
        Optional<User> existsUser = this.userService.getUserById(id);

        if (existsUser.isPresent()){
            return new ResponseEntity<>(existsUser, HttpStatus.OK);
        }

        return new ResponseEntity<>("Usuário não cadastrado", HttpStatus.NOT_FOUND);
    }
}
