# 🏦 API Financeira RESTful
Uma API robusta, escalável e segura para gestão de operações bancárias essenciais. Este projeto foi desenvolvido com foco em alta performance, consistência de dados (ACID) e arquitetura orientada a microsserviços, utilizando o ecossistema Java Spring Boot e infraestrutura em nuvem.

# 🚀 Visão Geral do Projeto
O objetivo desta API é simular o núcleo de operações (core banking) de uma instituição financeira. O sistema gerencia o ciclo de vida de clientes, contas bancárias e processa transações monetárias com rigorosos controles de integridade, garantindo que não ocorram inconsistências financeiras, mesmo em cenários de falha.

# 🛠️ Stack Tecnológica
Linguagem: Java 21

Framework: Spring Boot 3.x (Spring Web, Spring Data JPA, Spring Validation)

Banco de Dados: PostgreSQL

Infraestrutura Local: Docker & Docker Compose

Cloud Computing (Arquitetura Projetada): Amazon Web Services (AWS)

# ⚙️ Funcionalidades Implementadas (MVP)
Gestão de Usuários e Contas:

Cadastro de clientes com validação de dados únicos (CPF, E-mail).

Criação e vinculação automática de conta bancária com geração de número de conta no momento do cadastro.

Operações Financeiras (Transações):

Depósitos: Inserção de saldo de origem externa.

Saques: Retirada de fundos com validação estrita de saldo disponível.

Transferências P2P: Envio de valores entre contas da plataforma com processamento atômico (tudo ou nada) utilizando a anotação @Transactional.

Histórico e Auditoria:

Extrato: Consulta detalhada do histórico de movimentações (entradas e saídas) de uma conta específica, ordenadas cronologicamente.

# 🏗️ Arquitetura e Padrões de Projeto
O projeto segue a arquitetura em camadas (Layered Architecture) para garantir separação de responsabilidades e facilidade de manutenção:

Controller Layer: Responsável por interceptar requisições HTTP, validar o payload de entrada (DTOs) e retornar as respostas adequadas (Status Codes RESTful).

Service Layer: Onde reside a lógica de negócios e as regras de validação (ex: bloqueio de saque sem saldo).

Repository Layer: Implementação do padrão DAO (Data Access Object) via Spring Data JPA para comunicação fluida com o banco de dados.

Domain Layer: Entidades de negócio mapeadas diretamente para as tabelas do banco de dados relacional.

# 🔒 Roadmap e Próximos Passos (Evolução)
O projeto está em desenvolvimento contínuo e as próximas fases incluem integrações avançadas de segurança e escalabilidade:

Fase 3: Autenticação e Segurança:

Implementação do Spring Security.

Proteção de endpoints utilizando tokens JWT (JSON Web Tokens).

Criptografia irreversível de senhas no banco de dados utilizando BCrypt.

Fase 4: Migração para Cloud (AWS):

Hospedagem do banco de dados utilizando Amazon RDS (PostgreSQL).

Desacoplamento do processamento de transações pesadas utilizando filas de mensageria com Amazon SQS, garantindo resiliência contra picos de acesso.

Deploy da aplicação conteinerizada via AWS ECS ou Elastic Beanstalk.

# 💻 Como rodar o projeto localmente
Pré-requisitos:

Java 21+ instalado.

Docker e Docker Compose instalados.

Maven instalado.

Passo a passo:

Clone este repositório:

````Bash
git clone https://github.com/seu-usuario/api-financeira.git
Navegue até a pasta do projeto:

````Bash
cd api-financeira
Suba o banco de dados PostgreSQL utilizando o Docker:

````Bash
docker-compose up -d
Execute a aplicação Spring Boot:

````Bash
./mvnw spring-boot:run
A aplicação estará disponível e escutando requisições na porta 8080.