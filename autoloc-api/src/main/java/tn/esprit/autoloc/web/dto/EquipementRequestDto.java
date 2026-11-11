package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EquipementRequestDto(
        @NotBlank @Size(max = 80) String libelle
) {
}

