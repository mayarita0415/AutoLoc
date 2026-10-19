package tn.esprit.autoloc.service;

import java.math.BigDecimal;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Vehicule create(Vehicule vehicule) {
        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException("Un nouveau vehicule ne doit pas avoir d'identifiant");
        }
        validateBusinessRules(vehicule);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        validateBusinessRules(vehicule);

        Vehicule existant = findById(id);
        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setStatut(vehicule.getStatut());
        existant.setAgence(vehicule.getAgence());
        existant.setEquipements(vehicule.getEquipements());

        return vehiculeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    private void validateBusinessRules(Vehicule vehicule) {
        if (isBlank(vehicule.getImmatriculation())) {
            throw new IllegalArgumentException("L'immatriculation du vehicule est obligatoire");
        }
        if (isBlank(vehicule.getMarque())) {
            throw new IllegalArgumentException("La marque du vehicule est obligatoire");
        }
        if (isBlank(vehicule.getModele())) {
            throw new IllegalArgumentException("Le modele du vehicule est obligatoire");
        }
        if (vehicule.getCategorie() == null) {
            throw new IllegalArgumentException("La categorie du vehicule est obligatoire");
        }
        if (vehicule.getTarifJournalier() == null) {
            throw new IllegalArgumentException("Le tarif journalier est obligatoire");
        }
        if (vehicule.getTarifJournalier().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le tarif journalier ne peut pas etre negatif");
        }
        if (vehicule.getStatut() == null) {
            throw new IllegalArgumentException("Le statut du vehicule est obligatoire");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
