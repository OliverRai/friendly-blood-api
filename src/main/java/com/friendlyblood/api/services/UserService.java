package com.friendlyblood.api.services;



import com.friendlyblood.api.models.User;
import com.friendlyblood.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User saveUser(User newUser){
        return this.userRepository.save(newUser);
    }
}
