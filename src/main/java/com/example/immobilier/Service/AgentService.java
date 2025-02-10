package com.example.immobilier.Service;

import com.example.immobilier.Entity.Agent;
import com.example.immobilier.Repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentService {
    
    private AgentRepository agentRepository;

    @Autowired
    public AgentService(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    public Agent getAgentById(int id) {
        Optional<Agent> optionalAgent =  agentRepository.findById(id);
        return optionalAgent.orElse(null);
    }

    public Agent getAgentByName(String name) {
        Optional<Agent> optionalAgent =  agentRepository.findByNom(name);
        return optionalAgent.orElse(null);
    }

    public List<Agent> searchAgentsByName(String value) {
        Optional<List<Agent>> optionalAgents = agentRepository.findByNomContaining(value);
        return optionalAgents.orElse(null);
    }

    @Transactional
    public void deleteAgentById(int id) throws Exception {
        try {
            agentRepository.deleteById(id);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public void deleteAllAgents() throws Exception {
        try {
            agentRepository.deleteAll();
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Agent addAgent(Agent agent) {
        return agentRepository.save(agent);
    }

    @Transactional
    public Agent updateAgent(int agentId,Agent agent) {
        Optional<Agent> optionalAgent = agentRepository.findById(agentId);
        if(optionalAgent.isEmpty()){
            throw new IllegalStateException(
                    "agent with id "+agentId+" does not exists"
            );
        }
        var updateAgent = Agent.builder()
                .nom(agent.getNom())
                .prenom(agent.getPrenom())
                .image(agent.getImage())
                .telephone(agent.getTelephone())
                .contratsGeres(agent.getContratsGeres())
                .build();

        return agentRepository.save(updateAgent);
    }
}
