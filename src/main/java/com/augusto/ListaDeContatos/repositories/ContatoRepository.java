package com.augusto.ListaDeContatos.repositories;

import com.augusto.ListaDeContatos.models.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
}
