package com.augusto.ListaDeContatos.models;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    private String rua;

    private String cep;

    @ManyToOne
    @JoinColumn(name = "contato_id")
    private Contato contato;
}
