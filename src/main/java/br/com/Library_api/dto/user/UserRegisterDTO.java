package br.com.Library_api.dto.user;

import br.com.Library_api.domain.user.UserType;
import br.com.Library_api.dto.address.AddressDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


public record UserRegisterDTO(
        @Schema(example = "Maria Silva")
        @NotBlank
        String name,

        @Schema(example = "maria@email.com")
        @NotBlank
        @Email
        String email,

        @Schema(example = "1234567a", description = "Senha deve conter no mínimo 8 caracteres e possuir letras e números")
        @NotBlank
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
                message = "Password must have at least 8 characters, including one letter and one number."
        )
        String password,

        @Schema(example = "21993457687")
        @NotBlank
        String phone,

        @Schema(example = "STUDENT", description = "Papel do usuário no sistema")
        @NotNull
        UserType userType,

        @NotNull
        @Valid
        AddressDTO address
) {
}
