package br.com.Library_api.dto.user;

import br.com.Library_api.dto.address.AddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record PutUserDTO(
        @NotNull
        Long id,

        String name,

        @Email
        String email,

        String phone,

        @Valid
        AddressDTO address
) {
}
