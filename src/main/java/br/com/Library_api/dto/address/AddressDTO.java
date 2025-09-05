package br.com.Library_api.dto.address;

import jakarta.validation.constraints.NotBlank;

public record AddressDTO(
    @NotBlank
    String street,
    @NotBlank
    String district,
    @NotBlank
    String postalCode,
    @NotBlank
    String city,
    @NotBlank
    String state,

    String houseNumber,

    String complement

){
}
