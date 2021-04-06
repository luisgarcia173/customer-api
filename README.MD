# Customer API
![project version](https://img.shields.io/badge/version-1.0.0-blue.svg)
- **Time:** Luis Garcia
- **Desde:** 02/04/2021

**Propósito:** Criação de uma API de Clientes que seja possível criar, editar, listar e atualizar os dados por 
completo e também de forma granular.

**Descrição:** Este projeto servirá de Lab para validação das tecnologias descritas logo abaixo, dando subsídio tecnico 
para a validação de futuras arquiteturas.

Critérios utilizados para construção da **API**:
- Utilização de boas práticas de código;
- Utilização de padrões de projeto;
- Cobertura de testes (Unitários, Integrados e/ou de comportamento);
- Design de API RESTful.

Critérios utilizados para esteira de **DevOps**:
- Empacotamento utilizando Docker;
- Utilização dos demais itens como container Banco de Dados, Cache, Vault etc;
- Utilização de orquestrador de container;
- Hospedar em cloud.

# Fases do Projeto / Roadmap
Data|Fase|Descrição
---|---|---
02/04/2021|Configuração|Criação do esqueleto do projeto utilizando SpringBoot
02/04/2021|Documentação|Criação do arquivo README
02/04/2021|Configuração|Criação do repositório no Github
02/04/2021|Configuração|Adição da Lib do Actuator (Health Check)
02/04/2021|Configuração|Configuração do OpenApi para gerar swagger das APIs
02/04/2021|Configuração|Adição e configuração da Lib Jacoco (Cobertura de Testes)
02/04/2021|Configuração|Criação das classes e métodos de teste (TDD) baseado na especificação da API
02/04/2021|Desenvolvimento|Criação das Entidades e Enums conforme necessidade dos testes unitários
03/04/2021|Desenvolvimento|Criação dos DTOs para blindar o modelo do banco de dados 
03/04/2021|Desenvolvimento|Criação das Rotas e Endpoints para validação das chamadas e HTTPCodes 
03/04/2021|Desenvolvimento|Escrever documentação swagger para as rotas
03/04/2021|Configuração|Abstração de versionamento de APIs
04/04/2021|Configuração|Alteração na biblioteca de Documentação Api de OpenApi para Swagger3 devido a limitação no versionamento
04/04/2021|Configuração|Configurar Swagger para versionar as Endpoints
04/04/2021|Configuração|Configurar Actuator para responder porta 8080 por não conseguir direcionar swagger para outra porta
04/04/2021|Configuração|Alterada configuração Actuator para Config própria
04/04/2021|Configuração|Criada camada de segurança para proteger rotas do Actuator usando SpringSecurity
04/04/2021|Configuração|Adicionar banco H2 (inMemory) para validação da POC
04/04/2021|Configuração|Configurar camada persistência JPA
04/04/2021|Configuração|Inclusão de consulta paginada usando Pageable
04/04/2021|Desenvolvimento|Criação da Camada de Negócio chamadas aos métodos JPA
04/04/2021|Desenvolvimento|Criação das Exceções customizadas
05/04/2021|Configuração|Configuração do SpringDataJpa para mapear as entidades banco e validar persistência
05/04/2021|Desenvolvimento|Configuração do Handler de erros
05/04/2021|Configuração|Criação do SecurityConfig para liberar as rotas nos testes
06/04/2021|Desenvolvimento|Retomando os testes unitários por camadas (Resource/Business/Repository)
06/04/2021|Configuração|Validação do Jacoco com as métricas de qualidade
06/04/2021|Teste Integração|Criação dos payloads no postman para teste de integração
07/04/2021|Documentação|Atualização README

# Fora Escopo (Melhoria)
Data|Fase|Descrição
---|---|---
Pendente|Melhoria|Empacotamento aplicação utilizando Docker
Pendente|Melhoria|Banco de dados e demais itens de infra tbm containerizados
Pendente|Melhoria|Utilizar orquestrador Docker Compose ou Kubernetes
Pendente|Melhoria|Subir ambiente funcional em Cloud (gratuíta)
Pendente|Melhoria|Criação camada de log transacional (auditoria)
Pendente|Melhoria|Criação camada de log sistema (erros)
Pendente|Melhoria|Configurar monitoria aplicação utilizando Elastic Stack
Pendente|Melhoria|Criação camada de cache para consultas menos sensíveis (Redis)
Pendente|Melhoria|Viabilizar camada REST usando WebRedux (chamadas assíncronas)
Pendente|Melhoria|Alterar modelo do bando de dados para NoSQL e diminuir a dependência com Entidas aninhadas (MongoDB)
Pendente|Melhoria|Aumentar a granularidade das consultas mudando o modelo de REST para GraphQL
Pendente|Melhoria|Criar camada de mensageria para processar requisições mais pesadas (Kafka)
Pendente|Melhoria|Criação camada de segurança implementando Basic, JWT e oAuth
Pendente|Melhoria|Expor credenciais aplicação servidor de Vault

# Detalhamento Técnico

## Sumário
1. [Estrutura do Projeto](#estrutura-projeto)
1. [Divisão dos Pacotes](#divisao-pacotes)
1. [Dependências](#dependencias)
1. [Open API e Swagger Docs](#open-api-e-swagger-docs)
1. [DTO](#dto)
1. [Persistance Layer](#persistance-layer)
1. [Custom Exceptions](#custom-exceptions)
1. [Unit Tests](#unit-tests)
1. [Code Coverage](#code-coverage)
1. [API Health Check](#api-health-check)
1. [Authentication](#authentication)
1. [Logs e Monitoramento](#logs-e-monitoramento)

## Estrutura do Projeto
To-do descritivo
To-do desenho arquitetural

## Divisão dos Pacotes
To-do

## Dependências
To-do

## Open API e Swagger Docs
To-do

## DTO
To-do

## Persistance Layer
To-do

## Custom Exceptions
To-do

## Unit Tests
To-do

## Code Coverage
To-do

## Authentication
To-do

## API Health Check
To-do

## Logs e Monitoramento
To-do
