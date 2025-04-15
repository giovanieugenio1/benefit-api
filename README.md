
## API de Gestão de Benefícios Corporativos
API para gerenciamento de benefícios corporativos (vale-refeição, vale-transporte, plano de saúde, etc.) oferecidos aos colaboradores de uma empresa. Permite cadastro de benefícios, colaboradores e associação entre eles, além de geração de relatórios e histórico de alterações.
## Tecnologias

- Java 17 - Linguagem principal

- Spring Boot - Framework para desenvolvimento da API REST

- Spring Security - Autenticação e autorização

- JWT - Autenticação stateless

- Spring Data JPA - Persistência de dados

- PostgreSQL - Banco de dados relacional

- Docker - Containerização do banco de dados

- Lombok - Redução de boilerplate code

- Swagger/OpenAPI - Documentação da API

- JUnit 5 & Mockito - Testes unitários e de integração


## Pré-requisitos
- Java 17+

- Docker (opcional para banco de dados local)

- Maven 3.6+

- PostgreSQL 12+
## Configuração do Ambiente
#### Com Docker (recomendado) - Execução no Windows
docker run --name benefit-db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=benefit-db -p 5432:5432 -d postgres:13

### Autenticação
A API utiliza JWT (JSON Web Tokens) para autenticação. Endpoints protegidos requerem o header: Authorization: Bearer <token>  -> teste no Postman para facilidade

Rotas de Autenticação: 

**POST /auth/login** -> Autentica usuário e retorna token JWT

**POST /auth/register** -> Registra novo usuário (apenas ADMIN)
## Documentação da API
Acesse a documentação interativa em:

http://localhost:8080/swagger-ui.html


## Endpoints

### Employee

Cria novo colaborador - POST (ADMIN)
```http
  http://localhost:8080/employee
```
Obtém colaborador por ID - GET
```http
  http://localhost:8080/employee/1 
```
Atualiza colaborador - PUT (ADMIN)
```http
  http://localhost:8080/employee/1
```
Remove colaborador - DELETE (ADMIN)
```http
  http://localhost:8080/employee/1
```
Lista todos os colaboradores
```http
  http://localhost:8080/employee
```

### Benefit 

Cria um novo benefício - POST (ADMIN)
```http
  http://localhost:8080/benefit
```
Atualiza um benefício - PUT (ADMIN)
```http
  http://localhost:8080/benefit/1
```
Obtém um benefício por ID - GET
```http
  http://localhost:8080/benefit/1
```
Remove um benefício - DELETE (ADMIN)
```http
  http://localhost:8080/benefit/1
```
Lista todos os benefícios - GET
```http
  http://localhost:8080/benefit
```
### Módulo de Usuários (User)
Endpoints para gerenciamento de usuários do sistema contém as mesmas funções. Requer autenticação JWT e as operações de escrita são restritas a usuários com role ADMIN.

Para facilitar os envios das requisições use a collection do Postman anexada neste repositório.