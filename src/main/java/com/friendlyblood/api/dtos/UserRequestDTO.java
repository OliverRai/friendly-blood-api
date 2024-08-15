package com.friendlyblood.api.dtos;

public record UserRequestDTO(String email, String password, String name, String address, String bloodType) {
}
