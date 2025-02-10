package com.example.immobilier.Repository;

import com.example.immobilier.Entity.Agent;
import com.example.immobilier.Entity.BienImmobilier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BiensRepository extends JpaRepository<BienImmobilier,Integer> {
    Optional<BienImmobilier> findByNom(String nom);
    Optional<List<BienImmobilier>> findByNomContaining(String nom);
}
