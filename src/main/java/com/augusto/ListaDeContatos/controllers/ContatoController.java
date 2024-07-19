package com.augusto.ListaDeContatos.controllers;

import com.augusto.ListaDeContatos.models.Contato;
import com.augusto.ListaDeContatos.models.Endereco;
import com.augusto.ListaDeContatos.repositories.ContatoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/contato")
@Tag(name = "Lista de Contatos")
public class ContatoController {

    @Autowired
    private ContatoRepository contatoRepository;

    @Operation(summary = "Busca todos os contatos na base de dados", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contatos encontrados com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum contato encontrado")
    })
    @GetMapping
    public ResponseEntity<List<Contato>> getAllContatos() {
        List<Contato> contatos = contatoRepository.findAll();

        if (contatos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(contatos, HttpStatus.OK);
    }

    @Operation(summary = "Cadastra um novo contato", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contato cadastrado com sucesso")
    })
    @PostMapping
    public ResponseEntity<Contato> createContato(@RequestBody Contato contato) {

        for (Endereco endereco : contato.getEnderecos()) {
            endereco.setContato(contato);
        }

        contatoRepository.save(contato);
        return new ResponseEntity<>(contato, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza todos os dados de um contato", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado")
    })
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

    @Operation(summary = "Atualiza somente os dados especificados de um contato", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Contato> updateItemsContato(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Contato> contatoOptional = contatoRepository.findById(id);

        if (contatoOptional.isPresent()) {
            Contato contatoExistente = contatoOptional.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "nome":
                        contatoExistente.setNome((String) value);
                        break;

                    case "email":
                        contatoExistente.setEmail((String) value);
                        break;

                    case "telefone":
                        contatoExistente.setTelefone((String) value);
                        break;

                    case "dataNascimento":
                        contatoExistente.setDataNascimento(LocalDate.parse((String) value));
                        break;

                    case "enderecos":
                        contatoExistente.getEnderecos().clear();
                        List<Endereco> novosEnderecos = (List<Endereco>) value;
                        for (Endereco endereco : novosEnderecos) {
                            endereco.setContato(contatoExistente);
                            contatoExistente.getEnderecos().add(endereco);
                        }
                        break;

                    default:
                        throw new RuntimeException("Campo desconhecido: " + key);
                }
            });
            contatoRepository.save(contatoExistente);
            return new ResponseEntity<>(contatoExistente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Deleta um contato", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Contato deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Contato> deleteContato(@PathVariable Long id) {
        Optional<Contato> contatoOptional = contatoRepository.findById(id);

        if (contatoOptional.isPresent()) {
            contatoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
