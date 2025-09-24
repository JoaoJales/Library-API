package br.com.Library_api.dto.user;

import br.com.Library_api.dto.address.AddressDTO;
import br.com.Library_api.dto.address.PutAddressDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record PutUserDTO(
        @Schema(example = "1")
        @NotNull
        Long userId,

        @Schema(example = "Name example")
        String name,

        @Schema(example = "example@email.com")
        @Email
        String email,

        @Schema(example = "41992346537")
        String phone,

        @Valid
        PutAddressDTO address
) {
}
