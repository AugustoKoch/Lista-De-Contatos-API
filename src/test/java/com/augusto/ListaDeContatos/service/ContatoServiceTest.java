package com.augusto.ListaDeContatos.service;

import com.augusto.ListaDeContatos.model.Contato;
import com.augusto.ListaDeContatos.repository.ContatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ContatoServiceTest {

    @Mock
    private ContatoRepository contatoRepository;

    @InjectMocks
    private ContatoService contatoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar OK quando contatos exitem")
    void getAllContatosCase1() {
        List<Contato> contatos = Arrays.asList(new Contato(), new Contato());
        when(contatoRepository.findAll()).thenReturn(contatos);

        ResponseEntity<List<Contato>> response = contatoService.getAllContatos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contatos, response.getBody());
    }

    @Test
    @DisplayName("Deve retornar NO_CONTENT quando contatos não exitem")
    void getAllContatosCase2() {
        when(contatoRepository.findAll()).thenReturn( new ArrayList<>());

        ResponseEntity<List<Contato>> response = contatoService.getAllContatos();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void createContato() {
    }

    @Test
    void updateContato() {
    }

    @Test
    void updateItemsContato() {
    }

    @Test
    void deleteContato() {
    }
}