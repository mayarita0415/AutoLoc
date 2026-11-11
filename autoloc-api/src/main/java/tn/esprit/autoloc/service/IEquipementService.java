package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.EquipementRequestDto;
import tn.esprit.autoloc.web.dto.EquipementResponseDto;

import java.util.List;

public interface IEquipementService {
    EquipementResponseDto create(EquipementRequestDto equipement);
    EquipementResponseDto findById(Long id);
    List<EquipementResponseDto> findAll();
    EquipementResponseDto update(Long id, EquipementRequestDto equipement);
    void deleteById(Long id);
}
