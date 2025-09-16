package br.com.Library_api.infra.security;

import br.com.Library_api.domain.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    public User getPrincipalUserLogged(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            throw new IllegalStateException("No authenticated users found");
        }

        return user;
    }

    public Long getLoggedUserId() {
        return getPrincipalUserLogged().getId();
    }

}
