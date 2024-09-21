package com.friendlyblood.api.domain.models;

import java.util.UUID;

import com.friendlyblood.api.domain.enums.BloodType;
import com.friendlyblood.api.domain.models.baseModel.BaseEntity;
import com.friendlyblood.api.dtos.User.UserRequestDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type", nullable = false)
    private BloodType bloodType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    public User(UserRequestDTO payload) {
        this.email = payload.email();
        this.password = payload.password();
        this.name = payload.name();
        this.bloodType = payload.bloodType();
        this.address = new Address(payload.address());
    }
}