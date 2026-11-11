package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.VehiculeResponseDto;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class VehiculeMapper {

    public VehiculeResponseDto toResponse(Vehicule vehicule) {
        Long agenceId = vehicule.getAgence() != null ? vehicule.getAgence().getIdAgence() : null;
        Set<Long> equipementIds = vehicule.getEquipements() == null
                ? Set.of()
                : vehicule.getEquipements().stream().map(Equipement::getIdEquipement).collect(Collectors.toSet());

        return new VehiculeResponseDto(
                vehicule.getIdVehicule(),
                vehicule.getImmatriculation(),
                vehicule.getMarque(),
                vehicule.getModele(),
                vehicule.getCategorie(),
                vehicule.getTarifJournalier(),
                vehicule.getStatut(),
                agenceId,
                equipementIds
        );
    }
}

