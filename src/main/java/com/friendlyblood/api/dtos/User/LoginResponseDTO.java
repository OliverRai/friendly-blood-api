package com.friendlyblood.api.dtos.User;

import java.util.Optional;

public record LoginResponseDTO(String token) {

  public LoginResponseDTO(Optional<String> token) {
    this(token.orElse(null));
  }
}
