package com.augusto.ListaDeContatos.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Contato {

    @Id
    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private LocalDate DataNascimento;

    private List<Endereco> enderecos;
}
