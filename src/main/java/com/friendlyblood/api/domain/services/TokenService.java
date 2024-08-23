package com.friendlyblood.api.domain.services;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.friendlyblood.api.domain.models.User;

@Service
public class TokenService {
  public String generateToken(User user) {
    try {
      Algorithm algorithm = Algorithm.HMAC256("secret");
      return JWT.create()
          .withIssuer("FriendlyBlood API")
          .withSubject(user.getId().toString())
          .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
          .sign(algorithm);
    } catch (JWTCreationException exception) {
      throw new RuntimeException("Error creating token");
    }
  }
}
