package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.web.dto.ClientRequestDto;
import tn.esprit.autoloc.web.dto.ClientResponseDto;
import tn.esprit.autoloc.web.mapper.ClientMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientResponseDto create(ClientRequestDto dto) {
        Client client = clientMapper.toEntity(dto);
        return clientMapper.toResponse(clientRepository.save(client));
    }

    @Override
    public ClientResponseDto findById(Long id) {
        return clientMapper.toResponse(findClientEntity(id));
    }

    @Override
    public List<ClientResponseDto> findAll() {
        return clientRepository.findAll().stream().map(clientMapper::toResponse).toList();
    }

    @Override
    public ClientResponseDto update(Long id, ClientRequestDto dto) {
        Client existant = findClientEntity(id);
        existant.setNom(dto.nom());
        existant.setPrenom(dto.prenom());
        existant.setEmail(dto.email());
        existant.setTelephone(dto.telephone());
        existant.setNumPermis(dto.numPermis());
        existant.setDateInscription(dto.dateInscription());
        return clientMapper.toResponse(clientRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }
        clientRepository.deleteById(id);
    }

    private Client findClientEntity(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }
}
