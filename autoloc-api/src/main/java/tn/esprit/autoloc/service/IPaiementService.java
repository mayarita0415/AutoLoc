package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.PaiementResponseDto;

import java.util.List;

public interface IPaiementService {
    PaiementResponseDto findById(Long id);
    List<PaiementResponseDto> findAll();
}
