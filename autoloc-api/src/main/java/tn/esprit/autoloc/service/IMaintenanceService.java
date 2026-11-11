package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.MaintenanceRequestDto;
import tn.esprit.autoloc.web.dto.MaintenanceResponseDto;

import java.util.List;

public interface IMaintenanceService {
    MaintenanceResponseDto create(MaintenanceRequestDto maintenance);
    MaintenanceResponseDto findById(Long id);
    List<MaintenanceResponseDto> findAll();
    MaintenanceResponseDto update(Long id, MaintenanceRequestDto maintenance);
    void deleteById(Long id);
}
