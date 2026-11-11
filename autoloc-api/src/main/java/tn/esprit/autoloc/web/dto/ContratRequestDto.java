package tn.esprit.autoloc.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record ContratRequestDto(
        @NotNull LocalDate dateSignature,
        @NotNull @DecimalMin("0.0") BigDecimal montantTotal,
        @NotNull Boolean valide,
        Long reservationId,
        Set<@Valid PaiementCreateDto> paiements
) {
}

