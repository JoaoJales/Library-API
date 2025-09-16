package br.com.Library_api.controller;

import br.com.Library_api.domain.user.User;
import br.com.Library_api.dto.user.userAuthorization.UserAuthorizationDTO;
import br.com.Library_api.infra.security.DTOTokenJWT;
import br.com.Library_api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/")
public class AuthorizationController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthorizationController (AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserAuthorizationDTO data){
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());

        var authentication = authenticationManager.authenticate(authenticationToken);

        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new DTOTokenJWT(tokenJWT));
    }
}
