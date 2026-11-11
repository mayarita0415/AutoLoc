package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.web.dto.EmployeResponseDto;

@Component
public class EmployeMapper {

    public EmployeResponseDto toResponse(Employe employe) {
        Long agenceId = employe.getAgence() != null ? employe.getAgence().getIdAgence() : null;
        return new EmployeResponseDto(
                employe.getIdEmploye(),
                employe.getNom(),
                employe.getPrenom(),
                employe.getRole(),
                agenceId
        );
    }
}

