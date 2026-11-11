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
import tn.esprit.autoloc.service.IReservationService;
import tn.esprit.autoloc.web.dto.ReservationRequestDto;
import tn.esprit.autoloc.web.dto.ReservationResponseDto;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservations", description = "Gestion des reservations")
public class ReservationController {

    private final IReservationService reservationService;

    @Operation(summary = "Lister toutes les reservations")
    @ApiResponse(responseCode = "200", description = "Liste des reservations recuperee")
    @GetMapping
    public List<ReservationResponseDto> findAll() {
        return reservationService.findAll();
    }

    @Operation(summary = "Consulter une reservation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation trouvee"),
            @ApiResponse(responseCode = "404", description = "Reservation introuvable")
    })
    @GetMapping("/{id}")
    public ReservationResponseDto findById(@Parameter(description = "Identifiant de la reservation") @PathVariable Long id) {
        return reservationService.findById(id);
    }

    @Operation(summary = "Creer une reservation")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reservation creee"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponseDto create(@Valid @RequestBody ReservationRequestDto reservation) {
        return reservationService.create(reservation);
    }

    @Operation(summary = "Modifier une reservation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation modifiee"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "404", description = "Reservation introuvable")
    })
    @PutMapping("/{id}")
    public ReservationResponseDto update(@PathVariable Long id, @Valid @RequestBody ReservationRequestDto reservation) {
        return reservationService.update(id, reservation);
    }

    @Operation(summary = "Supprimer une reservation")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reservation supprimee"),
            @ApiResponse(responseCode = "404", description = "Reservation introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        reservationService.deleteById(id);
    }
}
