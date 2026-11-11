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

import tn.esprit.autoloc.service.IContratService;
import tn.esprit.autoloc.web.dto.ContratRequestDto;
import tn.esprit.autoloc.web.dto.ContratResponseDto;

import java.util.List;

@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
@Tag(name = "Contrats", description = "Gestion des contrats de location")
public class ContratController {

    private final IContratService contratService;

    @Operation(summary = "Lister tous les contrats")
    @ApiResponse(responseCode = "200", description = "Liste des contrats recuperee")
    @GetMapping
    public List<ContratResponseDto> findAll() {
        return contratService.findAll();
    }

    @Operation(summary = "Consulter un contrat")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contrat trouve"),
            @ApiResponse(responseCode = "404", description = "Contrat introuvable")
    })
    @GetMapping("/{id}")
    public ContratResponseDto findById(@Parameter(description = "Identifiant du contrat") @PathVariable Long id) {
        return contratService.findById(id);
    }

    @Operation(summary = "Creer un contrat")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Contrat cree"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContratResponseDto create(@Valid @RequestBody ContratRequestDto contrat) {
        return contratService.create(contrat);
    }

    @Operation(summary = "Modifier un contrat")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contrat modifie"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "404", description = "Contrat introuvable")
    })
    @PutMapping("/{id}")
    public ContratResponseDto update(@PathVariable Long id, @Valid @RequestBody ContratRequestDto contrat) {
        return contratService.update(id, contrat);
    }

    @Operation(summary = "Supprimer un contrat")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Contrat supprime"),
            @ApiResponse(responseCode = "404", description = "Contrat introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        contratService.deleteById(id);
    }
}