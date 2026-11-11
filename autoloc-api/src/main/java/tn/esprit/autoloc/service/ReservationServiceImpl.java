package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.ReservationRequestDto;
import tn.esprit.autoloc.web.dto.ReservationResponseDto;
import tn.esprit.autoloc.web.mapper.ReservationMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public ReservationResponseDto create(ReservationRequestDto dto) {
        validateBusinessRules(dto);
        Reservation reservation = new Reservation();
        applyDto(reservation, dto);
        return reservationMapper.toResponse(reservationRepository.save(reservation));
    }

    @Override
    public ReservationResponseDto findById(Long id) {
        return reservationMapper.toResponse(findReservationEntity(id));
    }

    @Override
    public List<ReservationResponseDto> findAll() {
        return reservationRepository.findAll().stream().map(reservationMapper::toResponse).toList();
    }

    @Override
    public ReservationResponseDto update(Long id, ReservationRequestDto dto) {
        validateBusinessRules(dto);
        Reservation existante = findReservationEntity(id);
        applyDto(existante, dto);
        return reservationMapper.toResponse(reservationRepository.save(existante));
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }

    private void applyDto(Reservation reservation, ReservationRequestDto dto) {
        reservation.setDateDebut(dto.dateDebut());
        reservation.setDateFin(dto.dateFin());
        reservation.setStatut(dto.statut());
        reservation.setVehicule(findVehiculeEntity(dto.vehiculeId()));
        reservation.setClient(findClientEntity(dto.clientId()));
    }

    private void validateBusinessRules(ReservationRequestDto dto) {
        if (dto.dateFin().isBefore(dto.dateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas etre anterieure a la date de debut");
        }
    }

    private Reservation findReservationEntity(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }

    private Vehicule findVehiculeEntity(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    private Client findClientEntity(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }
}
