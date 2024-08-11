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

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
    @DisplayName("Retorna OK quando existem contatos na lista")
    void getAllContatosCase1() {
        List<Contato> contatos = Arrays.asList(new Contato(), new Contato());
        when(contatoRepository.findAll()).thenReturn(contatos);

        ResponseEntity<List<Contato>> response = contatoService.getAllContatos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contatos, response.getBody());
    }

    @Test
    @DisplayName("Retorna NO_CONTENT quando não existem contatos na lista")
    void getAllContatosCase2() {
        when(contatoRepository.findAll()).thenReturn( new ArrayList<>());

        ResponseEntity<List<Contato>> response = contatoService.getAllContatos();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Retorna CREATED ao criar um novo contato")
    void createContato() {
        Contato contato = new Contato();
        when(contatoRepository.save(contato)).thenReturn(contato);

        ResponseEntity<Contato> response = contatoService.createContato(contato);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(contato, response.getBody());
        verify(contatoRepository, times(1)).save(contato);
    }

    @Test
    @DisplayName("Retorna OK ao atualizar um contato existente")
    void updateContatoCase1() {
        Long contatoId = 1L;
        Contato contatoExistente = new Contato();
        Contato contato = new Contato(2L, "Augusto", "augusto@teste.com",
                "48999999999", LocalDate.of(2001, 4, 5), new ArrayList<>() );

        when(contatoRepository.findById(contatoId)).thenReturn(Optional.of(contatoExistente));
        when(contatoRepository.save(contatoExistente)).thenReturn(contatoExistente);

        ResponseEntity<Contato> response = contatoService.updateContato(contatoId, contato);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contato.getNome(), response.getBody().getNome());
        assertEquals(contato.getEmail(), response.getBody().getEmail());
        assertEquals(contato.getTelefone(), response.getBody().getTelefone());
        assertEquals(contato.getDataNascimento(), response.getBody().getDataNascimento());
        assertEquals(contato.getEnderecos(), response.getBody().getEnderecos());
        verify(contatoRepository, times(1)).save(contatoExistente);
    }

    @Test
    @DisplayName("Retorna NOT_FOUND ao tentar atualizar um contato inexistente")
    void updateContatoCase2() {
        Long contatoId = 1L;
        Contato contato = new Contato();
        when(contatoRepository.findById(contatoId)).thenReturn(Optional.empty());

        ResponseEntity<Contato> response = contatoService.updateContato(contatoId, contato);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Retorna OK ao atualizar campos específicos de um contato existente")
    void updateItemsContatoCase1() {
        Long contatoId = 1L;
        Contato existingContato = new Contato();
        existingContato.setEnderecos(new ArrayList<>());
        when(contatoRepository.findById(contatoId)).thenReturn(Optional.of(existingContato));

        Map<String, Object> updates = new HashMap<>();
        updates.put("nome", "Augusto");
        updates.put("email", "augusto@teste.com");
        updates.put("telefone", "48999999999");
        updates.put("dataNascimento", "2001-04-05");
        updates.put("enderecos", new ArrayList<>());

        ResponseEntity<Contato> response = contatoService.updateItemsContato(contatoId, updates);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Augusto", existingContato.getNome());
        assertEquals("augusto@teste.com", existingContato.getEmail());
        assertEquals("48999999999", existingContato.getTelefone());
        assertEquals(LocalDate.of(2001, 4, 5), existingContato.getDataNascimento());
        assertEquals(new ArrayList<>(), existingContato.getEnderecos());
        verify(contatoRepository, times(1)).save(existingContato);
    }

    @Test
    @DisplayName("Retorna NOT_FOUND ao tentar atualizar campos de um contato inexistente")
    void updateItemsContatoCase2() {
        Long contatoId = 1L;
        Map<String, Object> updates = new HashMap<>();

        when(contatoRepository.findById(contatoId)).thenReturn(Optional.empty());

        ResponseEntity<Contato> response = contatoService.updateItemsContato(contatoId, updates);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Retorna NO_CONTENT ao deletar um contato existente")
    void deleteContatoCase1() {
        Long contatoId = 1L;
        Contato contato = new Contato();
        when(contatoRepository.findById(contatoId)).thenReturn(Optional.of(contato));

        ResponseEntity<Contato> response = contatoService.deleteContato(contatoId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(contatoRepository, times(1)).deleteById(contatoId);
    }

    @Test
    @DisplayName("Retorna NOT_FOUND ao tentar deletar um contato inexistente")
    void deleteContatoCase2() {
        Long contatoId = 1L;
        when(contatoRepository.findById(contatoId)).thenReturn(Optional.empty());

        ResponseEntity<Contato> response = contatoService.deleteContato(contatoId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}