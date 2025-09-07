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

@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String phone;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(UserRegisterDTO data){
        this.name = data.name();
        this.password = data.password();
        this.email = data.email();
        this.phone = data.phone();
        this.userType = data.userType();
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

}
