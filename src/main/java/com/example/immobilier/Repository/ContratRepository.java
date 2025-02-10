package com.example.immobilier.Repository;

import com.example.immobilier.Entity.Contrat;
import com.example.immobilier.Entity.ExtraType.TypeContrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContratRepository extends JpaRepository<Contrat, Integer> {
    Optional<List<Contrat>> findByTypeContrat(TypeContrat typeContrat);
    Optional<List<Contrat>> findByDateContrat(String dateContrat);
    Optional<List<Contrat>> findByMontant(double montant);
}
