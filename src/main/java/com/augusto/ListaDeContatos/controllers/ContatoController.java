package com.augusto.ListaDeContatos.controllers;

import com.augusto.ListaDeContatos.models.Contato;
import com.augusto.ListaDeContatos.repositories.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contato")
public class ContatoController {

    @Autowired
    private ContatoRepository contatoRepository;

    @GetMapping
    public ResponseEntity<List<Contato>> getAllContatos() {
        List<Contato> contatos = contatoRepository.findAll();

        if (contatos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(contatos, HttpStatus.OK);
    }



}
