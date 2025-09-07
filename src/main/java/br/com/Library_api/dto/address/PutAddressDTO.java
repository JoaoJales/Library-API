package br.com.Library_api.dto.address;


public record PutAddressDTO(
    String street,
    String district,
    String postalCode,
    String city,
    String state,

    String houseNumber,

    String complement

){
}
