package com.augusto.ListaDeContatos.controllers;

import com.augusto.ListaDeContatos.models.Contato;
import com.augusto.ListaDeContatos.models.Endereco;
import com.augusto.ListaDeContatos.repositories.ContatoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping
    public ResponseEntity<Contato> createContato(@RequestBody Contato contato) {

        for (Endereco endereco : contato.getEnderecos()) {
            endereco.setContato(contato);
        }

        contatoRepository.save(contato);
        return new ResponseEntity<>(contato, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contato> updateContato(@PathVariable Long id, @RequestBody Contato contato) {
        Optional<Contato> contatoOptional = contatoRepository.findById(id);

        if (contatoOptional.isPresent()) {
            Contato contatoExistente = contatoOptional.get();
            BeanUtils.copyProperties(contato, contatoExistente, "id", "enderecos");

            contatoExistente.getEnderecos().clear();
            for (Endereco endereco : contato.getEnderecos()) {
                endereco.setContato(contatoExistente);
                contatoExistente.getEnderecos().add(endereco);
            }

            contatoRepository.save(contatoExistente);
            return new ResponseEntity<>(contatoExistente, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
