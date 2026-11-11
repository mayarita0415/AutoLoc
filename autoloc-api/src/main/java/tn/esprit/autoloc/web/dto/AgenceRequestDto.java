package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AgenceRequestDto(
        @NotBlank @Size(max = 80) String nom,
        @NotBlank @Size(max = 80) String ville,
        @NotBlank @Size(max = 150) String adresse,
        @NotBlank @Size(max = 20) String telephone
) {
}

