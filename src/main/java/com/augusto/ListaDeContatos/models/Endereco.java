package com.augusto.ListaDeContatos.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    private String rua;

    private String cep;

    @ManyToOne
    @JoinColumn(name = "contato_id")
    @JsonBackReference("contato-endereco")
    private Contato contato;
}
