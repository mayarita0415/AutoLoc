package tn.esprit.autoloc.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record ContratResponseDto(
        Long idContrat,
        LocalDate dateSignature,
        BigDecimal montantTotal,
        Boolean valide,
        Long reservationId,
        Set<PaiementResponseDto> paiements
) {
}

