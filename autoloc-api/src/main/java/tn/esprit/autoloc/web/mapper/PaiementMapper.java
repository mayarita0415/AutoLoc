package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.web.dto.PaiementCreateDto;
import tn.esprit.autoloc.web.dto.PaiementResponseDto;

@Component
public class PaiementMapper {

    public Paiement toEntity(PaiementCreateDto dto) {
        Paiement paiement = new Paiement();
        paiement.setMontant(dto.montant());
        paiement.setDatePaiement(dto.datePaiement());
        paiement.setModePaiement(dto.modePaiement());
        return paiement;
    }

    public PaiementResponseDto toResponse(Paiement paiement) {
        Long contratId = paiement.getContrat() != null ? paiement.getContrat().getIdContrat() : null;
        return new PaiementResponseDto(
                paiement.getIdPaiement(),
                paiement.getMontant(),
                paiement.getDatePaiement(),
                paiement.getModePaiement(),
                contratId
        );
    }
}

