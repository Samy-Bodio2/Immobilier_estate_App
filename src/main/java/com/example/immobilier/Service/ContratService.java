package com.example.immobilier.Service;

import com.example.immobilier.Entity.Contrat;
import com.example.immobilier.Entity.ExtraType.TypeContrat;
import com.example.immobilier.Repository.ContratRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContratService {

    public ContratRepository contratRepository;
    public ContratService(ContratRepository contratRepository) {
        this.contratRepository = contratRepository;
    }

    public List<Contrat> getAllContrats() {
        return contratRepository.findAll();
    }

    public Contrat getContratById(int id) {
        Optional<Contrat> optionalContrats =  contratRepository.findById(id);
        return optionalContrats.orElse(null);
    }

    public List<Contrat> searchContratsByTypeContrat(TypeContrat value) {
        Optional<List<Contrat>> optionalContrats = contratRepository.findByTypeContrat(value);
        return optionalContrats.orElse(null);
    }

    public List<Contrat> searchContratsByDateContrat(String value) {
        Optional<List<Contrat>> optionalContrats = contratRepository.findByDateContrat(value);
        return optionalContrats.orElse(null);
    }

    public List<Contrat> searchContratsByMontant(double value) {
        Optional<List<Contrat>> optionalContrats = contratRepository.findByMontant(value);
        return optionalContrats.orElse(null);
    }

    @Transactional
    public void deleteContratById(int id) throws Exception {
        try {
            contratRepository.deleteById(id);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public void deleteAllContrats() throws Exception {
        try {
            contratRepository.deleteAll();
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Contrat addContrat(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Transactional
    public Contrat updateContrat(int ContratId,Contrat contrat) {
        Optional<Contrat> optionalContrat = contratRepository.findById(ContratId);
        if(optionalContrat.isEmpty()){
            throw new IllegalStateException(
                    "Contrat with id "+ContratId+" does not exists"
            );
        }
        var updateContrat = Contrat.builder()
                .typeContrat(contrat.getTypeContrat())
                .dateContrat(contrat.getDateContrat())
                .montant(contrat.getMontant())
                .bienImmobilier(contrat.getBienImmobilier())
                .client(contrat.getClient())
                .bienImmobilier(contrat.getBienImmobilier())
                .proprietaire(contrat.getProprietaire())
                .build();

        return contratRepository.save(updateContrat);
    }
}
