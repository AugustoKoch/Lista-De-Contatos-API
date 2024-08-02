package com.augusto.ListaDeContatos.service;

import com.augusto.ListaDeContatos.models.Contato;
import com.augusto.ListaDeContatos.models.Endereco;
import com.augusto.ListaDeContatos.repositories.ContatoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    public ResponseEntity<List<Contato>> getAllContatos() {
        List<Contato> contatos = contatoRepository.findAll();

        if (contatos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(contatos, HttpStatus.OK);
    }

    public ResponseEntity<Contato> createContato(Contato contato) {
        for (Endereco endereco : contato.getEnderecos()) {
            endereco.setContato(contato);
        }

        contatoRepository.save(contato);
        return new ResponseEntity<>(contato, HttpStatus.CREATED);
    }

    public ResponseEntity<Contato> updateContato(Long id, Contato contato) {
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

    public ResponseEntity<Contato> updateItemsContato(Long id, Map<String, Object> updates) {
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

    public ResponseEntity<Contato> deleteContato(Long id) {
        Optional<Contato> contatoOptional = contatoRepository.findById(id);

        if (contatoOptional.isPresent()) {
            contatoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
