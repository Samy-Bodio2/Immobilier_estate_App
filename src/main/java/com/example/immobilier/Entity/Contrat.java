package com.example.immobilier.Entity;

import com.example.immobilier.Entity.ExtraType.TypeContrat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Contrat")
@Builder
public class Contrat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idContrat;

    @Column(name = "typeContrat")
    private TypeContrat typeContrat;

    @Column(name = "dateContrat")
    private String dateContrat;

    @Column(name = "montant")
    private double montant;

    @Column
    private String bienName;

    @Column
    private String clientName;

    @Column
    private String propName;

    @Column
    private String associateName;

    @ManyToOne
    @JoinColumn(name = "idBien", nullable = true)
    private BienImmobilier bienImmobilier;

    @ManyToOne
    @JoinColumn(name = "idClient", nullable = true)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "idPropritaire", nullable = true)
    private Proprietaire proprietaire;
}
