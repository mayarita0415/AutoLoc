package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.EmployeRequestDto;
import tn.esprit.autoloc.web.dto.EmployeResponseDto;

import java.util.List;

public interface IEmployeService {
    EmployeResponseDto create(EmployeRequestDto employe);
    EmployeResponseDto findById(Long id);
    List<EmployeResponseDto> findAll();
    EmployeResponseDto update(Long id, EmployeRequestDto employe);
    void deleteById(Long id);
}
