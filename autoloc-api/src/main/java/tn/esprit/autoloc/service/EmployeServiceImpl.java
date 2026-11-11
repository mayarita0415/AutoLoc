package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEmployeRepository;
import tn.esprit.autoloc.web.dto.EmployeRequestDto;
import tn.esprit.autoloc.web.dto.EmployeResponseDto;
import tn.esprit.autoloc.web.mapper.EmployeMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;
    private final IAgenceRepository agenceRepository;
    private final EmployeMapper employeMapper;

    @Override
    public EmployeResponseDto create(EmployeRequestDto dto) {
        Employe employe = new Employe();
        applyDto(employe, dto);
        return employeMapper.toResponse(employeRepository.save(employe));
    }

    @Override
    public EmployeResponseDto findById(Long id) {
        return employeMapper.toResponse(findEmployeEntity(id));
    }

    @Override
    public List<EmployeResponseDto> findAll() {
        return employeRepository.findAll().stream().map(employeMapper::toResponse).toList();
    }

    @Override
    public EmployeResponseDto update(Long id, EmployeRequestDto dto) {
        Employe existant = findEmployeEntity(id);
        applyDto(existant, dto);
        return employeMapper.toResponse(employeRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }
        employeRepository.deleteById(id);
    }

    private void applyDto(Employe employe, EmployeRequestDto dto) {
        employe.setNom(dto.nom());
        employe.setPrenom(dto.prenom());
        employe.setRole(dto.role());
        employe.setAgence(findAgenceEntity(dto.agenceId()));
    }

    private Employe findEmployeEntity(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
    }

    private Agence findAgenceEntity(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }
}
