package com.augusto.ListaDeContatos.controllers;

import com.augusto.ListaDeContatos.models.Contato;
import com.augusto.ListaDeContatos.service.ContatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contato")
@Tag(name = "Lista de Contatos")
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    @Operation(summary = "Busca todos os contatos na base de dados", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contatos encontrados com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum contato encontrado")
    })
    @GetMapping
    public ResponseEntity<List<Contato>> getAllContatos() {
        return contatoService.getAllContatos();
    }

    @Operation(summary = "Cadastra um novo contato", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contato cadastrado com sucesso")
    })
    @PostMapping
    public ResponseEntity<Contato> createContato(@RequestBody Contato contato) {
        return contatoService.createContato(contato);
    }

    @Operation(summary = "Atualiza todos os dados de um contato", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Contato> updateContato(@PathVariable Long id, @RequestBody Contato contato) {
        return contatoService.updateContato(id, contato);
    }

    @Operation(summary = "Atualiza somente os dados especificados de um contato", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Contato> updateItemsContato(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return contatoService.updateItemsContato(id, updates);
    }

    @Operation(summary = "Deleta um contato", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Contato deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Contato> deleteContato(@PathVariable Long id) {
        return contatoService.deleteContato(id);
    }
}
