package com.example.immobilier.Entity;

import com.example.immobilier.Entity.ExtraType.CategorieBien;
import com.example.immobilier.Entity.ExtraType.Statut;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@Table(name = "BienImmobilier")
@Builder
public class BienImmobilier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idBien;

    @Column
    private String nom;

    @Column
    private CategorieBien type;

    @Column
    private String adresse;

    @Column
    private double surface;

    @Column
    private int prix;

    @Column
    private String image;

    @Column
    private Statut statut;

    @Column
    private String dateAjout;

    @Column
    private String nameProp;

    @ManyToOne
    @JoinColumn(name = "idPropritaire", nullable = false)
    private Proprietaire proprietaire;

    @OneToMany(mappedBy = "bienImmobilier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contrat> contratList;
}
