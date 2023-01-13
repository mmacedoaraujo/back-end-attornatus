package com.example.mmacedoaraujo.registrationapi.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.web.PageableDefault;

import java.util.Objects;

@Getter
@Entity
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address_tb")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String logradouro;
    private String cep;
    private Integer numero;
    private String cidade;
    private boolean enderecoPrincipal;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIdentityReference(alwaysAsId = true)
    private User userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!Objects.equals(id, address.id)) return false;
        return Objects.equals(cep, address.cep);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cep != null ? cep.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "Address(id=" + this.getId() + ", logradouro=" + this.getLogradouro() + ", cep=" + this.getCep() + ", numero=" + this.getNumero() + ", cidade=" + this.getCidade() + ", enderecoPrincipal=" + this.isEnderecoPrincipal() + ", userId=" + this.userId.getId() + ")";
    }
}
