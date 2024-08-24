package com.friendlyblood.api.dtos.User;

import com.friendlyblood.api.domain.enums.BloodType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    String password,

    @NotBlank(message = "Name is required")
    String name,
    
    BloodType bloodType,

    @NotBlank(message = "Address is required")
    String address
) {}