package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.VehiculeRequestDto;
import tn.esprit.autoloc.web.dto.VehiculeResponseDto;
import tn.esprit.autoloc.web.mapper.VehiculeMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public VehiculeResponseDto create(VehiculeRequestDto dto) {
        Vehicule vehicule = new Vehicule();
        applyDto(vehicule, dto);
        return vehiculeMapper.toResponse(vehiculeRepository.save(vehicule));
    }

    @Override
    public VehiculeResponseDto findById(Long id) {
        return vehiculeMapper.toResponse(findVehiculeEntity(id));
    }

    @Override
    public List<VehiculeResponseDto> findAll() {
        return vehiculeRepository.findAll().stream().map(vehiculeMapper::toResponse).toList();
    }

    @Override
    public VehiculeResponseDto update(Long id, VehiculeRequestDto dto) {
        Vehicule existant = findVehiculeEntity(id);
        applyDto(existant, dto);
        return vehiculeMapper.toResponse(vehiculeRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    private void applyDto(Vehicule vehicule, VehiculeRequestDto dto) {
        vehicule.setImmatriculation(dto.immatriculation());
        vehicule.setMarque(dto.marque());
        vehicule.setModele(dto.modele());
        vehicule.setCategorie(dto.categorie());
        vehicule.setTarifJournalier(dto.tarifJournalier());
        vehicule.setStatut(dto.statut());
        vehicule.setAgence(findAgenceEntity(dto.agenceId()));
        vehicule.setEquipements(findEquipements(dto.equipementIds()));
    }

    private Vehicule findVehiculeEntity(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    private Agence findAgenceEntity(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }

    private Set<Equipement> findEquipements(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(ids.stream().map(this::findEquipementEntity).toList());
    }

    private Equipement findEquipementEntity(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
    }
}
