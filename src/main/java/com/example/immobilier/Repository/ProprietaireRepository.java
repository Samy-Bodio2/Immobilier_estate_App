package com.example.immobilier.Repository;

import com.example.immobilier.Entity.Client;
import com.example.immobilier.Entity.Proprietaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProprietaireRepository extends JpaRepository<Proprietaire, Integer> {
    Optional<Proprietaire> findByNom(String nom);
    Optional<List<Proprietaire>> findByNomContaining(String nom);
}
