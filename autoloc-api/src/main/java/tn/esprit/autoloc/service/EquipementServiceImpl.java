package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.web.dto.EquipementRequestDto;
import tn.esprit.autoloc.web.dto.EquipementResponseDto;
import tn.esprit.autoloc.web.mapper.EquipementMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;
    private final EquipementMapper equipementMapper;

    @Override
    public EquipementResponseDto create(EquipementRequestDto dto) {
        Equipement equipement = equipementMapper.toEntity(dto);
        return equipementMapper.toResponse(equipementRepository.save(equipement));
    }

    @Override
    public EquipementResponseDto findById(Long id) {
        return equipementMapper.toResponse(findEquipementEntity(id));
    }

    @Override
    public List<EquipementResponseDto> findAll() {
        return equipementRepository.findAll().stream().map(equipementMapper::toResponse).toList();
    }

    @Override
    public EquipementResponseDto update(Long id, EquipementRequestDto dto) {
        Equipement existant = findEquipementEntity(id);
        existant.setLibelle(dto.libelle());
        return equipementMapper.toResponse(equipementRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipement", id);
        }
        equipementRepository.deleteById(id);
    }

    private Equipement findEquipementEntity(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
    }
}
