package tn.esprit.autoloc.web.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.web.dto.ContratResponseDto;
import tn.esprit.autoloc.web.dto.PaiementResponseDto;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContratMapper {

    private final PaiementMapper paiementMapper;

    public ContratResponseDto toResponse(Contrat contrat) {
        Long reservationId = contrat.getReservation() != null ? contrat.getReservation().getIdReservation() : null;
        Set<PaiementResponseDto> paiements = contrat.getPaiements() == null
                ? Set.of()
                : contrat.getPaiements().stream().map(paiementMapper::toResponse).collect(Collectors.toSet());

        return new ContratResponseDto(
                contrat.getIdContrat(),
                contrat.getDateSignature(),
                contrat.getMontantTotal(),
                contrat.getValide(),
                reservationId,
                paiements
        );
    }
}

