package com.example.immobilier.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Table(name = "Proprietaire")
@Builder
public class Proprietaire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPropritaire;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "proprietaire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BienImmobilier> bienImmobiliers;
}
