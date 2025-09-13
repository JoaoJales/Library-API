package br.com.Library_api.dto.report;

import br.com.Library_api.domain.user.UserType;

public record UsersDebtorsDTO(Long userId, String name, String email, UserType userType, Long unpaidFinesCount) {
}
