package br.com.Library_api.dto.address;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AddressDTO(

        @Schema(example = "Rua das palmeiras", description = "logradouro")
        @NotBlank
        String street,

        @Schema(example = "Copacabana", description = "bairro")
        @NotBlank
        String district,

        @Schema(example = "22011034", description = "CEP")
        @NotBlank
        String postalCode,

        @Schema(example = "Rio de Janeiro", description = "cidade")
        @NotBlank
        String city,

        @Schema(example = "RJ", description = "UF")
        @NotBlank
        String state,

        @Schema(example = "50", description = "Número (Opcional")
        @NotBlank
        String houseNumber,

        @Schema(example = "Ap 501", description = "Complemento (Opcional")
        String complement

){
}
