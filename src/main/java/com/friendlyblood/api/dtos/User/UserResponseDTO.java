package com.friendlyblood.api.dtos.User;

import java.util.UUID;

import com.friendlyblood.api.domain.enums.BloodType;
import com.friendlyblood.api.domain.models.User;

public record UserResponseDTO(UUID id, String email, String name, BloodType bloodType) {

  public UserResponseDTO(User user) {
    this(user.getId(), user.getEmail(), user.getName(), user.getBloodType());
  }

}