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
import tn.esprit.autoloc.service.IClientService;
import tn.esprit.autoloc.web.dto.ClientRequestDto;
import tn.esprit.autoloc.web.dto.ClientResponseDto;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Gestion des clients")
public class ClientController {

    private final IClientService clientService;

    @Operation(summary = "Lister tous les clients")
    @ApiResponse(responseCode = "200", description = "Liste des clients recuperee")
    @GetMapping
    public List<ClientResponseDto> findAll() {
        return clientService.findAll();
    }

    @Operation(summary = "Consulter un client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client trouve"),
            @ApiResponse(responseCode = "404", description = "Client introuvable")
    })
    @GetMapping("/{id}")
    public ClientResponseDto findById(@Parameter(description = "Identifiant du client") @PathVariable Long id) {
        return clientService.findById(id);
    }

    @Operation(summary = "Creer un client")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Client cree"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponseDto create(@Valid @RequestBody ClientRequestDto client) {
        return clientService.create(client);
    }

    @Operation(summary = "Modifier un client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client modifie"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "404", description = "Client introuvable")
    })
    @PutMapping("/{id}")
    public ClientResponseDto update(@PathVariable Long id, @Valid @RequestBody ClientRequestDto client) {
        return clientService.update(id, client);
    }

    @Operation(summary = "Supprimer un client")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Client supprime"),
            @ApiResponse(responseCode = "404", description = "Client introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        clientService.deleteById(id);
    }
}
