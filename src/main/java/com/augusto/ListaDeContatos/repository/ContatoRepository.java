package com.augusto.ListaDeContatos.repository;

import com.augusto.ListaDeContatos.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
}
