package com.example.immobilier.Service;

import com.example.immobilier.Entity.BienImmobilier;
import com.example.immobilier.Repository.BiensRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BienService {
    public BiensRepository biensRepository;
    public BienService(BiensRepository biensRepository) {
        this.biensRepository = biensRepository;
    }

    public List<BienImmobilier> getAllBiens() {
        return biensRepository.findAll();
    }

    public BienImmobilier getBienById(int id) {
        Optional<BienImmobilier> optionalBiens =  biensRepository.findById(id);
        return optionalBiens.orElse(null);
    }

    public BienImmobilier getBienByName(String name) {
        Optional<BienImmobilier> optionalBien =  biensRepository.findByNom(name);
        return optionalBien.orElse(null);
    }

    public List<BienImmobilier> searchBiensByName(String value) {
        Optional<List<BienImmobilier>> optionalAgents = biensRepository.findByNomContaining(value);
        return optionalAgents.orElse(null);
    }

    @Transactional
    public void deleteBienById(int id) throws Exception {
        try {
            biensRepository.deleteById(id);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public void deleteAllBiens() throws Exception {
        try {
            biensRepository.deleteAll();
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public BienImmobilier addBienImmobilier(BienImmobilier bien) {
        return biensRepository.save(bien);
    }

    @Transactional
    public BienImmobilier updateBien(int bienId,BienImmobilier bien) {
        Optional<BienImmobilier> optionalBienImmobilier = biensRepository.findById(bienId);
        if(optionalBienImmobilier.isEmpty()){
            throw new IllegalStateException(
                    "Bien with id "+bienId+" does not exists"
            );
        }
        var updateBien = BienImmobilier.builder()
                .nom(bien.getNom())
                .type(bien.getType())
                .image(bien.getImage())
                .prix(bien.getPrix())
                .contratList(bien.getContratList())
                .adresse(bien.getAdresse())
                .surface(bien.getSurface())
                .statut(bien.getStatut())
                .dateAjout(bien.getDateAjout())
                .proprietaire(bien.getProprietaire())
                .build();

        return biensRepository.save(updateBien);
    }
}
