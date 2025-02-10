package com.example.immobilier.Service;

import com.example.immobilier.Entity.Client;
import com.example.immobilier.Entity.Client;
import com.example.immobilier.Repository.ClientRepository;
import com.example.immobilier.Repository.ClientRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

//    public ClientService() {
//    }

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(int id) {
        Optional<Client> optionalClients =  clientRepository.findById(id);
        return optionalClients.orElse(null);
    }

    public Client getClientByName(String name) {
        Optional<Client> optionalBien =  clientRepository.findByNom(name);
        return optionalBien.orElse(null);
    }

    public List<Client> searchClientsByName(String value) {
        Optional<List<Client>> optionalAgents = clientRepository.findByNomContaining(value);
        return optionalAgents.orElse(null);
    }

    @Transactional
    public void deleteClientById(int id) throws Exception {
        try {
            clientRepository.deleteById(id);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public void deleteAllClients() throws Exception {
        try {
            clientRepository.deleteAll();
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void addClient(Client bien){
        try {
            clientRepository.save(bien);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public Client updateClient(int clientId,Client client) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if(optionalClient.isEmpty()){
            throw new IllegalStateException(
                    "Client with id "+clientId+" does not exists"
            );
        }
        var updateBien = Client.builder()
                .nom(client.getNom())
                .email(client.getEmail())
                .telephone(client.getTelephone())
                .Contrats(client.getContrats())
                .build();

        return clientRepository.save(updateBien);
    }
}
