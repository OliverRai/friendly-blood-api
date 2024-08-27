package com.friendlyblood.api.domain.models;

import com.friendlyblood.api.domain.models.baseModel.BaseEntity;
import com.friendlyblood.api.dtos.Address.AddressRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private Integer number;

    @Column(name = "postal_code", nullable = false)
    private Integer postalCode;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String state;

    public Address(AddressRequestDTO addressRequestDTO) {
        this.street = addressRequestDTO.street();
        this.postalCode = addressRequestDTO.postalCode();
        this.state = addressRequestDTO.state();
        this.number = addressRequestDTO.number();
        this.district = addressRequestDTO.district();
    }
}