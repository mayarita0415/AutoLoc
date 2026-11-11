package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
giimport jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IEquipementService;
import tn.esprit.autoloc.web.dto.EquipementRequestDto;
import tn.esprit.autoloc.web.dto.EquipementResponseDto;

import java.util.List;

@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
@Tag(name = "Equipements", description = "Gestion des equipements")
public class EquipementController {

    private final IEquipementService equipementService;

    @Operation(summary = "Lister tous les equipements")
    @ApiResponse(responseCode = "200", description = "Liste des equipements recuperee")
    @GetMapping
    public List<EquipementResponseDto> findAll() {
        return equipementService.findAll();
    }

    @Operation(summary = "Consulter un equipement")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipement trouve"),
            @ApiResponse(responseCode = "404", description = "Equipement introuvable")
    })
    @GetMapping("/{id}")
    public EquipementResponseDto findById(@Parameter(description = "Identifiant de l'equipement") @PathVariable Long id) {
        return equipementService.findById(id);
    }

    @Operation(summary = "Creer un equipement")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Equipement cree"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipementResponseDto create(@Valid @RequestBody EquipementRequestDto equipement) {
        return equipementService.create(equipement);
    }

    @Operation(summary = "Modifier un equipement")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipement modifie"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "404", description = "Equipement introuvable")
    })
    @PutMapping("/{id}")
    public EquipementResponseDto update(@PathVariable Long id, @Valid @RequestBody EquipementRequestDto equipement) {
        return equipementService.update(id, equipement);
    }

    @Operation(summary = "Supprimer un equipement")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Equipement supprime"),
            @ApiResponse(responseCode = "404", description = "Equipement introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        equipementService.deleteById(id);
    }
}
