package tn.esprit.autoloc.web.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.web.dto.ReservationResponseDto;

@Component
public class ReservationMapper {

    public ReservationResponseDto toResponse(Reservation reservation) {
        Long vehiculeId = reservation.getVehicule() != null ? reservation.getVehicule().getIdVehicule() : null;
        Long clientId = reservation.getClient() != null ? reservation.getClient().getIdClient() : null;
        return new ReservationResponseDto(
                reservation.getIdReservation(),
                reservation.getDateDebut(),
                reservation.getDateFin(),
                reservation.getStatut(),
                vehiculeId,
                clientId
        );
    }
}

