package com.friendlyblood.api.domain.models;

import com.friendlyblood.api.dtos.UserRequestDTO;
import com.friendlyblood.api.domain.enums.BloodType;
import com.friendlyblood.api.domain.models.baseModel.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type", nullable = false)
    private BloodType bloodType;

    @Column(nullable = false)
    private String address;

    public User(UserRequestDTO payload){
        this.email = payload.email();
        this.password = payload.password();
        this.name = payload.name();
        this.bloodType = payload.bloodType();
        this.address = payload.address();
    }
}
