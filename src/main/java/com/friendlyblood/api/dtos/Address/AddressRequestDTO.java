package com.friendlyblood.api.dtos.Address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressRequestDTO(
        @NotBlank(message = "Street is required")
        String street,

        @NotNull(message = "Number is required")
        Integer number,

        @NotNull(message = "PostalCode is required")
        Integer postalCode,

        @NotBlank(message = "State is required")
        String state,

        @NotBlank(message = "district code is required")
        String district
) {}