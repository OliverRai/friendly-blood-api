package com.friendlyblood.api.controllers;

import com.friendlyblood.api.domain.models.User;
import com.friendlyblood.api.domain.services.UserService;
import com.friendlyblood.api.dtos.User.LoginRequestDTO;
import com.friendlyblood.api.dtos.User.LoginResponseDTO;
import com.friendlyblood.api.dtos.User.UserRequestDTO;
import com.friendlyblood.api.dtos.User.UserResponseDTO;

import jakarta.transaction.Transactional;
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
    private UserService userService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDTO userRequestBody) {
        User user = new User(userRequestBody);

        if (this.userService.getUserByEmail(user.getEmail()).isPresent()){
            return new ResponseEntity<>("Email já cadastrado", HttpStatus.FOUND);
        }

        User savedUser = this.userService.createUser(user);

        UserResponseDTO userResponseBody = new UserResponseDTO(savedUser);
        return new ResponseEntity<>(userResponseBody, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserByEmail(@PathVariable UUID id){
        Optional<User> existsUser = this.userService.getUserById(id);

        if (existsUser.isPresent()){
            UserResponseDTO userResponseBody = new UserResponseDTO(existsUser.get());
            return new ResponseEntity<>(userResponseBody, HttpStatus.OK);
        }

        return new ResponseEntity<>("Usuário não cadastrado", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO login){
        Optional<String> token = this.userService.login(login.email(), login.password());

        if (token.isPresent()){
            return new ResponseEntity<>(new LoginResponseDTO(token), HttpStatus.OK);
        }

        return new ResponseEntity<>("Usuário não cadastrado", HttpStatus.NOT_FOUND);
    }
}
