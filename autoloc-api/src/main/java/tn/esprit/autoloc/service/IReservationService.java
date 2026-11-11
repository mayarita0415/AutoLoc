package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.ReservationRequestDto;
import tn.esprit.autoloc.web.dto.ReservationResponseDto;

import java.util.List;

public interface IReservationService {
    ReservationResponseDto create(ReservationRequestDto reservation);
    ReservationResponseDto findById(Long id);
    List<ReservationResponseDto> findAll();
    ReservationResponseDto update(Long id, ReservationRequestDto reservation);
    void deleteById(Long id);
}
