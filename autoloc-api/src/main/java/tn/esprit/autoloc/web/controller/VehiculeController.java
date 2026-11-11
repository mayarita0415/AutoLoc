package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.web.dto.VehiculeRequestDto;
import tn.esprit.autoloc.web.dto.VehiculeResponseDto;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Tag(name = "Vehicules", description = "Gestion des vehicules")
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @Operation(summary = "Lister tous les vehicules")
    @ApiResponse(responseCode = "200", description = "Liste des vehicules recuperee")
    @GetMapping
    public List<VehiculeResponseDto> findAll() {
        return vehiculeService.findAll();
    }

    @Operation(summary = "Consulter un vehicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicule trouve"),
            @ApiResponse(responseCode = "404", description = "Vehicule introuvable")
    })
    @GetMapping("/{id}")
    public VehiculeResponseDto findById(@Parameter(description = "Identifiant du vehicule") @PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @Operation(summary = "Creer un vehicule")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Vehicule cree"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeResponseDto create(@Valid @RequestBody VehiculeRequestDto vehicule) {
        return vehiculeService.create(vehicule);
    }

    @Operation(summary = "Modifier un vehicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicule modifie"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "404", description = "Vehicule introuvable")
    })
    @PutMapping("/{id}")
    public VehiculeResponseDto update(@PathVariable Long id, @Valid @RequestBody VehiculeRequestDto vehicule) {
        return vehiculeService.update(id, vehicule);
    }

    @Operation(summary = "Supprimer un vehicule")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Vehicule supprime"),
            @ApiResponse(responseCode = "404", description = "Vehicule introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        vehiculeService.deleteById(id);
    }
}
