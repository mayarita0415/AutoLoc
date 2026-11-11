package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.web.dto.AgenceRequestDto;
import tn.esprit.autoloc.web.dto.AgenceResponseDto;
import tn.esprit.autoloc.web.mapper.AgenceMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final AgenceMapper agenceMapper;

    @Override
    public AgenceResponseDto create(AgenceRequestDto dto) {
        Agence agence = agenceMapper.toEntity(dto);
        return agenceMapper.toResponse(agenceRepository.save(agence));
    }

    @Override
    public AgenceResponseDto findById(Long id) {
        return agenceMapper.toResponse(findAgenceEntity(id));
    }

    @Override
    public List<AgenceResponseDto> findAll() {
        return agenceRepository.findAll().stream().map(agenceMapper::toResponse).toList();
    }

    @Override
    public AgenceResponseDto update(Long id, AgenceRequestDto dto) {
        Agence existante = findAgenceEntity(id);
        existante.setNom(dto.nom());
        existante.setVille(dto.ville());
        existante.setAdresse(dto.adresse());
        existante.setTelephone(dto.telephone());
        return agenceMapper.toResponse(agenceRepository.save(existante));
    }

    @Override
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }
        agenceRepository.deleteById(id);
    }

    private Agence findAgenceEntity(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }
}
