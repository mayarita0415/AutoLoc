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
import tn.esprit.autoloc.service.IEmployeService;
import tn.esprit.autoloc.web.dto.EmployeRequestDto;
import tn.esprit.autoloc.web.dto.EmployeResponseDto;

import java.util.List;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
@Tag(name = "Employes", description = "Gestion des employes")
public class EmployeController {

    private final IEmployeService employeService;

    @Operation(summary = "Lister tous les employes")
    @ApiResponse(responseCode = "200", description = "Liste des employes recuperee")
    @GetMapping
    public List<EmployeResponseDto> findAll() {
        return employeService.findAll();
    }

    @Operation(summary = "Consulter un employe")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employe trouve"),
            @ApiResponse(responseCode = "404", description = "Employe introuvable")
    })
    @GetMapping("/{id}")
    public EmployeResponseDto findById(@Parameter(description = "Identifiant de l'employe") @PathVariable Long id) {
        return employeService.findById(id);
    }

    @Operation(summary = "Creer un employe")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employe cree"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeResponseDto create(@Valid @RequestBody EmployeRequestDto employe) {
        return employeService.create(employe);
    }

    @Operation(summary = "Modifier un employe")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employe modifie"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "404", description = "Employe introuvable")
    })
    @PutMapping("/{id}")
    public EmployeResponseDto update(@PathVariable Long id, @Valid @RequestBody EmployeRequestDto employe) {
        return employeService.update(id, employe);
    }

    @Operation(summary = "Supprimer un employe")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Employe supprime"),
            @ApiResponse(responseCode = "404", description = "Employe introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        employeService.deleteById(id);
    }
}
