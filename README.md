
# API Bank Account

## Sobre o Projeto

Este projeto é uma API desenvolvida para o gerenciamento de contas bancárias, com funcionalidades como depósito, saque e transferência entre contas. Ele foi criado utilizando Java Spring Boot, com o diferencial de incorporar Spring Security, garantindo que operações sensíveis só sejam realizadas por usuários autenticados.

## Funcionalidades Principais

- Criar conta bancária: Registro de novas contas com informações básicas.
- Consultar saldo: Verificação rápida dos saldos das contas.
- Depósitos e saques: Movimentação de valores de forma segura.
- Transferências entre contas: Transferência de valores entre contas cadastradas.
- Autenticação por token: Operações como depósitos, saques e transferências exigem um token gerado via Spring Security no momento do registro.

## Tecnologias Utilizadas

- Java Spring Boot: Framework para construção da API.
- Spring Security: Implementação de autenticação e autorização.
- Hibernate: Gerenciamento de banco de dados.
- H2 Database: Base de dados em memória para testes e desenvolvimento.

## Ferramenta de Teste

- Insominia: Ferramenta utilizada para validar e testar os endpoints da API.
