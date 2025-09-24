package br.com.Library_api.dto.report;

import br.com.Library_api.domain.user.UserType;
import io.swagger.v3.oas.annotations.media.Schema;

public record UsersDebtorsDTO(
        @Schema(example = "7")
        Long userId,

        @Schema(example = "Sofia Mendes")
        String name,

        @Schema(example = "sofia.mendes@email.com")
        String email,

        @Schema(example = "STUDENT")
        UserType userType,

        @Schema(example = "1")
        Long unpaidFinesCount) {
}
