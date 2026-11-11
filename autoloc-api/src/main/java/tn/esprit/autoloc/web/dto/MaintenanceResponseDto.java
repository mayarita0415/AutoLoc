package tn.esprit.autoloc.web.dto;

import java.time.LocalDate;

public record MaintenanceResponseDto(
        Long idMaintenance,
        LocalDate dateDebut,
        LocalDate dateFin,
        String description,
        Long vehiculeId
) {
}

