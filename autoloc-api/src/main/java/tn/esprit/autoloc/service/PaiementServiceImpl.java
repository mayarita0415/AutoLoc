package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IPaiementRepository;
import tn.esprit.autoloc.web.dto.PaiementResponseDto;
import tn.esprit.autoloc.web.mapper.PaiementMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;
    private final PaiementMapper paiementMapper;

    @Override
    public PaiementResponseDto findById(Long id) {
        return paiementRepository.findById(id)
                .map(paiementMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
    }

    @Override
    public List<PaiementResponseDto> findAll() {
        return paiementRepository.findAll().stream().map(paiementMapper::toResponse).toList();
    }
}
