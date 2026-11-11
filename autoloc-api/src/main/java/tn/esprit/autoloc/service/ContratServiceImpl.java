package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.web.dto.ContratRequestDto;
import tn.esprit.autoloc.web.dto.ContratResponseDto;
import tn.esprit.autoloc.web.dto.PaiementCreateDto;
import tn.esprit.autoloc.web.mapper.ContratMapper;
import tn.esprit.autoloc.web.mapper.PaiementMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;
    private final PaiementMapper paiementMapper;
    private final ContratMapper contratMapper;

    @Override
    public ContratResponseDto create(ContratRequestDto dto) {
        Contrat contrat = new Contrat();
        applyDto(contrat, dto, true);
        return contratMapper.toResponse(contratRepository.save(contrat));
    }

    @Override
    public ContratResponseDto findById(Long id) {
        return contratMapper.toResponse(findContratEntity(id));
    }

    @Override
    public List<ContratResponseDto> findAll() {
        return contratRepository.findAll().stream().map(contratMapper::toResponse).toList();
    }

    @Override
    public ContratResponseDto update(Long id, ContratRequestDto dto) {
        Contrat existant = findContratEntity(id);
        applyDto(existant, dto, false);
        return contratMapper.toResponse(contratRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }

    private void applyDto(Contrat contrat, ContratRequestDto dto, boolean includePaiements) {
        contrat.setDateSignature(dto.dateSignature());
        contrat.setMontantTotal(dto.montantTotal());
        contrat.setValide(dto.valide());
        contrat.setReservation(findReservation(dto.reservationId()));

        if (includePaiements) {
            contrat.setPaiements(buildPaiements(dto.paiements(), contrat));
        }
    }

    private Set<Paiement> buildPaiements(Set<PaiementCreateDto> paiementsDto, Contrat contrat) {
        if (paiementsDto == null || paiementsDto.isEmpty()) {
            return new HashSet<>();
        }

        Set<Paiement> paiements = new HashSet<>();
        for (PaiementCreateDto paiementDto : paiementsDto) {
            Paiement paiement = paiementMapper.toEntity(paiementDto);
            paiement.setContrat(contrat);
            paiements.add(paiement);
        }
        return paiements;
    }

    private Reservation findReservation(Long reservationId) {
        if (reservationId == null) {
            return null;
        }
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", reservationId));
    }

    private Contrat findContratEntity(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }
}
