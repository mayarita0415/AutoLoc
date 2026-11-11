package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.ContratRequestDto;
import tn.esprit.autoloc.web.dto.ContratResponseDto;

import java.util.List;

public interface IContratService {
    ContratResponseDto create(ContratRequestDto contrat);
    ContratResponseDto findById(Long id);
    List<ContratResponseDto> findAll();
    ContratResponseDto update(Long id, ContratRequestDto contrat);
    void deleteById(Long id);
}
