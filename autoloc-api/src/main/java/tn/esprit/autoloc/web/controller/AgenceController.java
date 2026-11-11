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
import tn.esprit.autoloc.service.IAgenceService;
import tn.esprit.autoloc.web.dto.AgenceRequestDto;
import tn.esprit.autoloc.web.dto.AgenceResponseDto;

import java.util.List;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
@Tag(name = "Agences", description = "Gestion des agences")
public class AgenceController {

    private final IAgenceService agenceService;

    @Operation(summary = "Lister toutes les agences")
    @ApiResponse(responseCode = "200", description = "Liste des agences recuperee")
    @GetMapping
    public List<AgenceResponseDto> findAll() {
        return agenceService.findAll();
    }

    @Operation(summary = "Consulter une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence trouvee"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable")
    })
    @GetMapping("/{id}")
    public AgenceResponseDto findById(@Parameter(description = "Identifiant de l'agence") @PathVariable Long id) {
        return agenceService.findById(id);
    }

    @Operation(summary = "Creer une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Agence creee"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgenceResponseDto create(@Valid @RequestBody AgenceRequestDto agence) {
        return agenceService.create(agence);
    }

    @Operation(summary = "Modifier une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence modifiee"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable")
    })
    @PutMapping("/{id}")
    public AgenceResponseDto update(@PathVariable Long id, @Valid @RequestBody AgenceRequestDto agence) {
        return agenceService.update(id, agence);
    }

    @Operation(summary = "Supprimer une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Agence supprimee"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        agenceService.deleteById(id);
    }
}
