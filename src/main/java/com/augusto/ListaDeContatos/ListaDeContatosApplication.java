package com.augusto.ListaDeContatos;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Lista de Contatos",
		version = "1.0", description = "Documentação para API de lista de contatos"))
@SpringBootApplication
public class ListaDeContatosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ListaDeContatosApplication.class, args);
	}

}
