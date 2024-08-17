package com.friendlyblood.api.domain.repositories;

import com.friendlyblood.api.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}