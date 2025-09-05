package br.com.Library_api.domain.address;

import br.com.Library_api.dto.address.AddressDTO;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String street;        // logradouro
    private String district;      // bairro (pode ser "neighborhood" também, mas "district" é mais comum em sistemas)
    private String postalCode;    // cep (nos EUA é "ZIP Code", mas em sistemas genéricos pode ser "postalCode")
    private String city;          // cidade
    private String state;         // uf (unidade federativa → estado)
    private String houseNumber;   // número
    private String complement;    // complemento (ex: apto, bloco, sala)


    public Address(AddressDTO data){
        this.street = data.street();
        this.district = data.district();
        this.postalCode = data.postalCode();
        this.city = data.city();
        this.state = data.state();
        this.houseNumber = data.houseNumber();
        this.complement = data.complement();
    }

    public void updateInfoAddress(AddressDTO data){
        if (data.street() != null) this.street = data.street();
        if (data.district() != null) this.district = data.district();
        if (data.postalCode() != null) this.postalCode = data.postalCode();
        if (data.city() != null) this.city = data.city();
        if (data.state() != null) this.state = data.state();
        if (data.houseNumber() != null) this.houseNumber = data.houseNumber();
        if (data.complement() != null) this.complement = data.complement();

    }
}
