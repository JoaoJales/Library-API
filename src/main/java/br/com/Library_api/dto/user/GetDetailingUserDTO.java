package br.com.Library_api.dto.user;

import br.com.Library_api.domain.address.Address;
import br.com.Library_api.domain.user.User;

public record GetDetailingUserDTO (Long userId, String name, String email, String phone, Address address){
    public GetDetailingUserDTO(User user){
        this(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getAddress());
    }
}
