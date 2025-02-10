package com.example.immobilier.Repository;

import com.example.immobilier.Entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Integer> {
    Optional<Agent> findByNom(String nom);
    Optional<List<Agent>> findByNomContaining(String nom);
}
