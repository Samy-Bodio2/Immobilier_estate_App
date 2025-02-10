package com.example.immobilier.Service;

import com.example.immobilier.Entity.Proprietaire;
import com.example.immobilier.Repository.ProprietaireRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProprietaireService {
    public ProprietaireRepository proprietaireRepository;
    public ProprietaireService(ProprietaireRepository proprietaireRepository) {
        this.proprietaireRepository = proprietaireRepository;
    }

    public List<Proprietaire> getAllProprietaires() {
        return proprietaireRepository.findAll();
    }

    public Proprietaire getProprietaireById(int id) {
        Optional<Proprietaire> optionalProprietaires =  proprietaireRepository.findById(id);
        return optionalProprietaires.orElse(null);
    }

    public Proprietaire getProprietaireByName(String name) {
        Optional<Proprietaire> optionalBien =  proprietaireRepository.findByNom(name);
        return optionalBien.orElse(null);
    }

    public List<Proprietaire> searchProprietairesByName(String value) {
        Optional<List<Proprietaire>> optionalAgents = proprietaireRepository.findByNomContaining(value);
        return optionalAgents.orElse(null);
    }

    @Transactional
    public void deleteProprietaireById(int id) throws Exception {
        try {
            proprietaireRepository.deleteById(id);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public void deleteAllProprietaires() throws Exception {
        try {
            proprietaireRepository.deleteAll();
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Proprietaire addProprietaire(Proprietaire bien) {
        return proprietaireRepository.save(bien);
    }

    @Transactional
    public Proprietaire updateProprietaire(int proprietaireId,Proprietaire proprietaire) {
        Optional<Proprietaire> optionalProprietaire = proprietaireRepository.findById(proprietaireId);
        if(optionalProprietaire.isEmpty()){
            throw new IllegalStateException(
                    "Proprietaire with id "+proprietaireId+" does not exists"
            );
        }
        var updateBien = Proprietaire.builder()
                .nom(proprietaire.getNom())
                .prenom(proprietaire.getPrenom())
                .email(proprietaire.getEmail())
                .telephone(proprietaire.getTelephone())
                .bienImmobiliers(proprietaire.getBienImmobiliers())
                .build();

        return proprietaireRepository.save(updateBien);
    }
}
