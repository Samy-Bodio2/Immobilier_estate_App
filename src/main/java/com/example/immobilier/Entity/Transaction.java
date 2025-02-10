package com.example.immobilier.Entity;

import com.example.immobilier.Entity.ExtraType.TypeTransaction;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
@Table(name = "Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idTransaction;

    @Column
    private String dateTransaction;

    @Column
    private double montant;

    @Column
    private String associateTransaction;

    @Column
    private TypeTransaction typeTransaction;

    @OneToOne
    @JoinColumn(name = "idContrat", nullable = false)
    private Contrat contratAssocie;
}
