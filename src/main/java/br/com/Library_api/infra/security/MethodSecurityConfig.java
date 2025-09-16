package br.com.Library_api.infra.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // habilita @PreAuthorize e @PostAuthorize
public class MethodSecurityConfig {

}
