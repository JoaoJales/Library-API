package br.com.Library_api.domain.user;

import br.com.Library_api.domain.address.Address;
import br.com.Library_api.dto.user.PutUserDTO;
import br.com.Library_api.dto.user.UserRegisterDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String phone;

    private boolean active;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(UserRegisterDTO data, String password){
        this.name = data.name();
        this.password = password;
        this.email = data.email();
        this.phone = data.phone();
        this.userType = data.userType();
        this.active = true;
        this.address = new Address(data.address());
    }

    public void updateInfoUser(@Valid PutUserDTO data) {
        if (data.name() != null) this.name = data.name();
        if (data.email() != null) this.email = data.email();
        if (data.phone() != null) this.phone = data.phone();
        if (data.address() != null){
            this.address.updateInfoAddress(data.address());
        }
    }

    public void deleteUser(){
        this.active = false;
    }

    public void updatePassword(String newPassword){
        this.password = newPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.userType.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
