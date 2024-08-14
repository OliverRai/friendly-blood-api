package com.friendlyblood.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "donation_proof")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonationProof {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String eTag;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;
}
