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
import tn.esprit.autoloc.service.IMaintenanceService;
import tn.esprit.autoloc.web.dto.MaintenanceRequestDto;
import tn.esprit.autoloc.web.dto.MaintenanceResponseDto;

import java.util.List;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
@Tag(name = "Maintenances", description = "Gestion des maintenances")
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    @Operation(summary = "Lister toutes les maintenances")
    @ApiResponse(responseCode = "200", description = "Liste des maintenances recuperee")
    @GetMapping
    public List<MaintenanceResponseDto> findAll() {
        return maintenanceService.findAll();
    }

    @Operation(summary = "Consulter une maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance trouvee"),
            @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    })
    @GetMapping("/{id}")
    public MaintenanceResponseDto findById(@Parameter(description = "Identifiant de la maintenance") @PathVariable Long id) {
        return maintenanceService.findById(id);
    }

    @Operation(summary = "Creer une maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Maintenance creee"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceResponseDto create(@Valid @RequestBody MaintenanceRequestDto maintenance) {
        return maintenanceService.create(maintenance);
    }

    @Operation(summary = "Modifier une maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance modifiee"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    })
    @PutMapping("/{id}")
    public MaintenanceResponseDto update(@PathVariable Long id, @Valid @RequestBody MaintenanceRequestDto maintenance) {
        return maintenanceService.update(id, maintenance);
    }

    @Operation(summary = "Supprimer une maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Maintenance supprimee"),
            @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        maintenanceService.deleteById(id);
    }
}
