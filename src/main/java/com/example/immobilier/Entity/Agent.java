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
@Table(name = "Agent")
@Builder
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idAgent;

    @Column
    private String nom;

    @Column
    private String prenom;

    @Column
    private String telephone;

    @Column
    private String image;

    @OneToMany
    private List<Contrat> contratsGeres;

}
