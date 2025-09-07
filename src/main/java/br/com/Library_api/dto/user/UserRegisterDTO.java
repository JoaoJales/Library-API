package br.com.Library_api.dto.user;

import br.com.Library_api.domain.user.UserType;
import br.com.Library_api.dto.address.AddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


public record UserRegisterDTO(
        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
                message = "Password must have at least 8 characters, including one letter and one number."
        )
        String password,

        @NotBlank
        String phone,

        @NotNull
        UserType userType,

        @NotNull
        @Valid
        AddressDTO address
) {
}
