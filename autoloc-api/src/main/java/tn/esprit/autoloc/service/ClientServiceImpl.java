package tn.esprit.autoloc.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;

    @Override
    public Client create(Client client) {
        if (client.getIdClient() != null) {
            throw new IllegalArgumentException("Un nouveau client ne doit pas avoir d'identifiant");
        }
        validateBusinessRules(client);
        return clientRepository.save(client);
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client update(Long id, Client client) {
        validateBusinessRules(client);

        Client existant = findById(id);
        existant.setNom(client.getNom());
        existant.setPrenom(client.getPrenom());
        existant.setEmail(client.getEmail());
        existant.setTelephone(client.getTelephone());
        existant.setNumPermis(client.getNumPermis());
        existant.setDateInscription(client.getDateInscription());

        return clientRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }
        clientRepository.deleteById(id);
    }

    private void validateBusinessRules(Client client) {
        if (isBlank(client.getNom())) {
            throw new IllegalArgumentException("Le nom du client est obligatoire");
        }
        if (isBlank(client.getPrenom())) {
            throw new IllegalArgumentException("Le prenom du client est obligatoire");
        }
        if (isBlank(client.getEmail())) {
            throw new IllegalArgumentException("L'email du client est obligatoire");
        }
        if (isBlank(client.getTelephone())) {
            throw new IllegalArgumentException("Le telephone du client est obligatoire");
        }
        if (isBlank(client.getNumPermis())) {
            throw new IllegalArgumentException("Le numero de permis du client est obligatoire");
        }
        if (client.getDateInscription() == null) {
            throw new IllegalArgumentException("La date d'inscription du client est obligatoire");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}

