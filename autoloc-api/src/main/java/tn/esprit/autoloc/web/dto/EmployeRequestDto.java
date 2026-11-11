package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tn.esprit.autoloc.domain.RoleEmploye;

public record EmployeRequestDto(
        @NotBlank @Size(max = 60) String nom,
        @NotBlank @Size(max = 60) String prenom,
        @NotNull RoleEmploye role,
        @NotNull Long agenceId
) {
}

