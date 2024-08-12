# Lista-De-Contatos-API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)

Este projeto é uma API para gravação e listagem de contatos, foi feita usando Java, Java Spring, MySQL como banco de dados, e documentada com Swagger.

A API foi feita para servir de portfólio, e também como material de estudo e teste de novas tecnologias.

Você também pode encontrar essa API rodando na AWS atravéz do link [Lista-De-Contatos-API](http://54.226.198.91:8080/swagger-ui/index.html#/)

## Pré-requisitos

Antes de começar, certifique-se de ter o seguinte instalado:

1. Java (JDK) 21
2. Maven 3.9.6
3. MySQL Workbench 8
4. MySQL Server 8


## Instalação

1. Clone o repositório:

   ```bash
   git clone https://github.com/AugustoKoch/Lista-De-Contatos-API.git

2. Navegue para o diretório do projeto

   ```bash
   cd Lista-De-Contatos-API

3. Configure o banco de dados:

    Crie um banco de dados MySQL e configure as credenciais no arquivo application.properties:

   ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/nome_do_banco_de_dados
   spring.datasource.username=usuario
   spring.datasource.password=senha

4. Compile e execute a aplicação:

   ```bash
   mvn spring-boot:run

A API ficará disponível em http://localhost:8080/contato


## API Endpoints
A API possui os seguintes endpoints:

```markdown
GET /contato - Retorna uma lista de todos os contato.

POST /contato - Registra um novo contato.

PUT /contato/{id} - Atualiza todos os dados de um contato.

PATCH /contato/{id} - Atualiza os dados especificados de um contato.

DELETE /contato/{id} - Deleta um contato.
```


## Testes

Os testes utilizam JUnit e Mockito para simular o comportamento do repositório e testar a lógica de negócio. Eles cobrem os seguintes cenários:

- **Retorna OK quando existem contatos na lista**: Verifica se a API retorna uma lista de contatos quando existem contatos cadastrados.
- **Retorna NO_CONTENT quando não existem contatos na lista**: Verifica se a API responde corretamente quando não há contatos para listar.
- **Retorna CREATED ao criar um novo contato**: Verifica se a criação de um novo contato retorna o status esperado.
- **Retorna OK ao atualizar um contato existente**: Testa a atualização completa de um contato existente.
- **Retorna NOT_FOUND ao tentar atualizar um contato inexistente**: Verifica se a API retorna o status correto quando tenta atualizar um contato que não existe.
- **Retorna OK ao atualizar campos específicos de um contato existente**: Testa a atualização parcial de um contato existente.
- **Retorna NOT_FOUND ao tentar atualizar campos de um contato inexistente**: Verifica se a API responde corretamente ao tentar atualizar campos de um contato inexistente.
- **Retorna NO_CONTENT ao deletar um contato existente**: Testa a remoção de um contato existente e verifica se o status correto é retornado.
- **Retorna NOT_FOUND ao tentar deletar um contato inexistente**: Verifica se a API retorna o status correto ao tentar deletar um contato que não existe.

Os testes estão localizados na pasta src/test/java/com/augusto/ListaDeContatos/service. Para executar os testes, você pode usar o seguinte comando na raiz do projeto:

   ```bash
   mvn test
   ```


## Documentação
A documentação completa da API é gerada usando o Swagger e pode ser acessada em http://localhost:8080/swagger-ui.html após iniciar a aplicação.


## Licença📝

Esse projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE.md) para mais detalhes.




