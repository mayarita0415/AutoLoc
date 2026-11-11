package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.web.dto.MaintenanceResponseDto;

@Component
public class MaintenanceMapper {

    public MaintenanceResponseDto toResponse(Maintenance maintenance) {
        Long vehiculeId = maintenance.getVehicule() != null ? maintenance.getVehicule().getIdVehicule() : null;
        return new MaintenanceResponseDto(
                maintenance.getIdMaintenance(),
                maintenance.getDateDebut(),
                maintenance.getDateFin(),
                maintenance.getDescription(),
                vehiculeId
        );
    }
}

