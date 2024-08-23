package com.friendlyblood.api.domain.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.friendlyblood.api.domain.models.User;
import com.friendlyblood.api.repositories.UserRepository;
import com.friendlyblood.api.utils.exceptions.InvalidPasswordException;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    public User createUser(User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return this.userRepository.save(newUser);
    }

    public Optional<User> getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public Optional<User> getUserById(UUID id) {
        return this.userRepository.findById(id);
    }

    public Optional<String> login(String email, String password) throws InvalidPasswordException, RuntimeException {
        Optional<User> user = this.getUserByEmail(email);
        if (!user.isPresent()) {
            return Optional.empty();
        }
        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new InvalidPasswordException();
        }
        return Optional.of(tokenService.generateToken(user.get()));
    }
}
