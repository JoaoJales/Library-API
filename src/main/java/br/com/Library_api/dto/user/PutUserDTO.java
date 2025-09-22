package br.com.Library_api.dto.user;

import br.com.Library_api.dto.address.AddressDTO;
import br.com.Library_api.dto.address.PutAddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record PutUserDTO(
        @NotNull
        Long userId,

        String name,

        @Email
        String email,

        String phone,

        @Valid
        PutAddressDTO address
) {
}
