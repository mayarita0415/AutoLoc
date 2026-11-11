package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.MaintenanceRequestDto;
import tn.esprit.autoloc.web.dto.MaintenanceResponseDto;
import tn.esprit.autoloc.web.mapper.MaintenanceMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final MaintenanceMapper maintenanceMapper;

    @Override
    public MaintenanceResponseDto create(MaintenanceRequestDto dto) {
        validateBusinessRules(dto);
        Maintenance maintenance = new Maintenance();
        applyDto(maintenance, dto);
        return maintenanceMapper.toResponse(maintenanceRepository.save(maintenance));
    }

    @Override
    public MaintenanceResponseDto findById(Long id) {
        return maintenanceMapper.toResponse(findMaintenanceEntity(id));
    }

    @Override
    public List<MaintenanceResponseDto> findAll() {
        return maintenanceRepository.findAll().stream().map(maintenanceMapper::toResponse).toList();
    }

    @Override
    public MaintenanceResponseDto update(Long id, MaintenanceRequestDto dto) {
        validateBusinessRules(dto);
        Maintenance existante = findMaintenanceEntity(id);
        applyDto(existante, dto);
        return maintenanceMapper.toResponse(maintenanceRepository.save(existante));
    }

    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        maintenanceRepository.deleteById(id);
    }

    private void applyDto(Maintenance maintenance, MaintenanceRequestDto dto) {
        maintenance.setDateDebut(dto.dateDebut());
        maintenance.setDateFin(dto.dateFin());
        maintenance.setDescription(dto.description());
        maintenance.setVehicule(findVehiculeEntity(dto.vehiculeId()));
    }

    private void validateBusinessRules(MaintenanceRequestDto dto) {
        if (dto.dateFin().isBefore(dto.dateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas etre anterieure a la date de debut");
        }
    }

    private Maintenance findMaintenanceEntity(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }

    private Vehicule findVehiculeEntity(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }
}
