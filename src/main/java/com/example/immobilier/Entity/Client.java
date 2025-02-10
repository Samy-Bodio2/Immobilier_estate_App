package com.example.immobilier.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Client")
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idClient ;

    @Column(name = "nom")
    private String nom;

    @Column(name = "phone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @Nullable
    private List<Contrat> Contrats;

    // Getters et Setters
}