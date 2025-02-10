package com.example.immobilier.Repository;

import com.example.immobilier.Entity.Agent;
import com.example.immobilier.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByNom(String nom);
    Optional<List<Client>> findByNomContaining(String nom);
}
