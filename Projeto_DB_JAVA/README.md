# CRUD de Pessoa e Endereço

    Este projeto foi desenvolvido em Java utilizando o framework Spring Boot para criar um CRUD (criar, ler, atualizar, deletar) para as entidades Pessoa e Endereço. A relação entre essas entidades é de um-para-muitos, isso significa que uma pessoa pode ter vários endereços.


 ## Funcionalidades

    O sistema oferece as seguintes funcionalidades:

    Listagem de Pessoas e Endereços
    Permite visualizar todas as pessoas cadastradas juntamente com seus endereços endereços.

    Cadastro de uma Nova Pessoa
    Permite criar uma nova pessoa no sistema, fornecendo informações como nome, data de nascimento e CPF.

    Cadastro de um Novo Endereço
    Permite criar um novo endereço no sistema, fornecendo o CPF da pessoa que você deseja vincular o endereço, a rua, número, bairro , cidade,  estado, CEP e se ele é o endereço principal.

    Atualização de Dados
    Permite atualizar os dados de uma pessoa, incluindo informações pessoais e endereços associados.

    Exclusão de Pessoa
    Permite excluir uma pessoa do sistema, juntamente com todos os seus endereços associados.

    Cálculo da Idade
    Calcula e exibe a idade da pessoa com base na sua data de nascimento.


## Tecnologias Utilizadas

    Java
    Spring Boot
    Spring Boot JPA
    Postgres SQL
    Docker 
    API Rest
    Requisições HTTP
    Json
    DTO
    Bean Validation
    Paginação e Ordenação 
    Testes Unitarios - Junit e Mockito


## Executando o Projeto

    Abra o projeto em sua IDE Java.
    Execute o seguinte comando no terminal : "docker compose up -d" para que o banco de dados seja criado remotamente no docker

## Desenvolvedor Responsável 

    Alexander Duarte
    linkedin : https://www.linkedin.com/in/alexander-f-duarte/
