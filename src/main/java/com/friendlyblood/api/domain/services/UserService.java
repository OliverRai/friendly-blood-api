package com.friendlyblood.api.domain.services;

import com.friendlyblood.api.domain.models.User;
import com.friendlyblood.api.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User saveUser(User newUser){
        return this.userRepository.save(newUser);
    }

    public Optional<User> getUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public Optional<User> getUserById(UUID id){
        return this.userRepository.findById(id);
    }
}
