package br.com.Library_api.dto.user;

import br.com.Library_api.domain.user.User;
import br.com.Library_api.domain.user.UserType;

public record GetUsersDTO(Long id, String name, String email, String phone, UserType userType) {
    public GetUsersDTO (User user){
        this(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getUserType());
    }
}
