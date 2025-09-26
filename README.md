# 📚 Library Api

A Library API é um sistema RESTful de gerenciamento para uma biblioteca acadêmica desenvolvido com **Java + Spring Boot**.
O projeto foi estruturado para simular as principais operações de uma biblioteca real, como empréstimos, devoluções, multas, reservas, controle de livros, autores e cópias.

Além disso, implementação de consultas SQL personalizadas:
-  **Consultas de usuário**: informações de empréstimos, reservas e multas.
-  **Consultas de administradores**: informações mais sensíveis das entidades como histórico de empréstimos de um livro e reservas ativas no sistema.
-  **Relatórios administrativos**: relatórios para análises como livros mais emprestados, autores mais lidos e melhores leitores.

---

### 📚 Sumário

1. [Principais Funcionalidades](#-principais-funcionalidades)
2. [Regras de Negócio](#-regras-de-negócio)
3. [Autenticação & Autorização](#-autenticação--autorização)
4. [Endpoints](#-endpoints-visão-geral)
   - [Usuários](#usuários)
   - [Administradores](#administradores)
   - [Livros, cópias e autores](#livros-cópias-e-autores)
   - [Empréstimos, reservas e multas](#empréstimos-reservas-e-multas)
5. [Exemplos de JSON](#-exemplos-de-json)
   - [Empréstimo](#empréstimo)
   - [Reserva](#reserva)
   - [Multa](#multa)
6. [Tecnologias utilizadas](#-tecnologias-utilizadas)
7. [Como rodar o projeto localmente](#️-como-rodar-o-projeto-localmente)
8. [Documentação](#-documentação)

--- 
## 📌 Principais Funcionalidades

- **Usuários**
  - Cadastro, autenticação e gestão de conta (dados pessoais, senha).
  - Consultas gerais e pessoais (empréstimos, reservas, multas, etc)
- **Livros**
  - Cadastro de **livros**, **autores** e **cópias**.
  - Consulta de acervo.
- **Reservas & Empréstimos**
  - Reserva de exemplares.
  - Retirada e devolução de livros.
  - Conversão de reserva em empréstimo.
- **Multas**
  - Controle automático de multas por atraso.
- **Admin**
  - Controle geral da biblioteca (livros, cópias, autores, etc).
- **Relatórios**
  - Consultas administrativas para gestão da biblioteca.

---

## 📘 Regras de Negócio

 👉 As regras de negócio detalhadas estão descritas em [`RULE.md`](./RULE.md).

---

## 🔑 Autenticação & Autorização

- Autenticação baseada em **JWT (JSON Web Token)**.
- Controle de acesso por **roles**:
  - `VISITOR` → acesso apenas a consultas básicas.
  - `STUDENT` / `PROFESSOR` → podem reservar, emprestar e devolver livros.
  - `ADMIN` → acesso total e endpoints administrativos (cadastros, relatórios, gestão de multas).

<details>
<summary style="font-size: 1.5em;"><strong>⚙️ Utilize esses usuários para Testes</strong></summary>

##### Admin:
```json
{
  "email":"admin@email.com",
  "password":"admin123"
}
```

##### Student (Estudante):
```json
{
  "email":"student@email.com",
  "password":"student1"
}
```

##### Professor:
```json
{
  "email":"professor@email.com",
  "password":"professor1"
}
```

</details>

---
## 📡 Endpoints (visão geral)

> POST -> `/populate` : Popular Banco de Dados (Opcional)

<details>
<summary style="font-size: 1.5em;"><strong>👤 Usuários</strong></summary>

| Método         | Endpoint                      | Descrição                                      |
|----------------|-------------------------------|------------------------------------------------|
| POST           | `/users/register`             | Cadastra um novo usuário                       |
| POST           | `/auth/login`                 | Realiza login e retorna token (JWT)            |
| GET            | `/users`                      | Consultar dados do usuário logado              |
| PUT            | `/users/password`             | Atualiza senha                                 |
| **Consultas:** |                               |                                                |
| GET            | `/users/loans`                | Busca histórico de empréstimos                 |
| GET            | `/users/loans/actives`        | Lista empréstimos ativos                       |
| GET            | `/users/reservations/actives` | Lista reservas ativas ou prontas para retirada |
| GET            | `/users/fines/unpaid`         | Lista multas não pagas                         |

</details>

<details>
<summary style="font-size: 1.5em;"><strong>👤 Administradores</strong></summary>

| Método          | Endpoint                       | Descrição                           |
|-----------------|--------------------------------|-------------------------------------|
| GET             | `/admin/users`                 | Lista todos os usuários ativos      |
| GET             | `/admin/loans`                 | Lista todos os empréstimos          |
| GET             | `admin/reservations`           | Lista todas as reservas             |
| **Relatórios:** |                                |                                     |
| GET             | `/reports/books/top`           | Lista os livros mais emprestados    |
| GET             | `/reports/users/top`           | Lista usuários com mais empréstimos |
| GET             | `/reports/books/availability`  | Lista disponibilidade dos livros    |
| GET             | `/reports/users/fines/top`     | Lista usuários com mais multas      |   

</details>


<details>
<summary style="font-size: 1.5em;"><strong>📚 Livros, cópias e autores</strong></summary>

##### POST, PUT e DELETE limitado ao `admin`

| Método | Endpoint             | Descrição                      |
|--------|----------------------|--------------------------------|
| POST   | `/admin/books`       | Registra Livro                 |
| POST   | `/admin/authors`     | Registrar Autor                |
| POST   | `/admin/bookCopies`  | Registrar Cópia de Livro       |
| DELETE | `/admin/books`       | Desativar Livro                |
| DELETE | `/admin/bookCopies`  | Desativar Livro                |
| GET    | `/books`             | Lista todos os livros          |
| GET    | `/books/{bookId}`    | Busca detalhamento de um livro |
| GET    | `/authors/{autorId}` | Busca detalhamento de um autor |

</details>


<details>
<summary style="font-size: 1.5em;"><strong>📑 Empréstimos, reservas e multas</strong></summary>

| Método | Endpoint                          | Descrição                 |
|--------|-----------------------------------|---------------------------|
| POST   | `/loans`                          | Cria um empréstimo        |
| PATCH  | `/loans/{loanId}/return`          | Retornar livro emprestado |
| PATCH  | `/loans/{loanId}/renew`           | Renova um empréstimo      |
| POST   | `/reservations`                   | Cria uma reserva          |
| PATCH  | `/reservations/{reservId}/pickup` | Retirar livro reservado   |
| DELETE | `/reservations/{reservId}`        | Cancela uma reserva       |
| PATCH  | `/fines/{fineId}/paid`            | Quitar multa              |

</details>

> OBS: Alguns endpoints restritos a administradores (Regras de Negócio real) foram liberados a outros usuários para testes. Para mais detalhes veja as regras liberadas em [`RULE.md`](./RULE.md)

---
## 📄 Exemplos de JSON

<details>
<summary style="font-size: 1.5em;"><strong>Empréstimo</strong></summary>

##### request:
```json
{
  "userId":"12",
  "bookCopyInventoryCode":"WS-H-1603-003"
}
```
##### response:
```json
{
  "loanId": 91,
  "userName": "João Ricardo",
  "titleBook": "Hamlet",
  "copy": "WS-H-1603-003",
  "loanDate": "2025-09-22",
  "dueDate": "2025-10-22",
  "returnDate": null,
  "status": "ACTIVE",
  "renewals": 0
}
```
</details>


<details>
<summary style="font-size: 1.5em;"><strong>Reserva</strong></summary>

##### request:
```json
{
  "bookId":"12"
}
```
##### response:
```json
{
  "reservationId": 11,
  "userId": 12,
  "name": "João Ricardo",
  "bookId": 12,
  "title": "Crime e Castigo",
  "reservationDate": "2025-09-22T16:39:38.9268386",
  "expiredDate": null,
  "status": "ACTIVE"
}
```
</details>

<details>
<summary style="font-size: 1.5em;"><strong>Multa</strong></summary>

```json
{
  "fineId": 15,
  "username": "Beatriz Martins",
  "amount": 5.00,
  "issuedDate": "2025-09-20",
  "paid": true,
  "loan": {
    "loanId": 81,
    "bookTitle": "A Hora da Estrela",
    "inventoryCode": "CL-HE-1977-002",
    "loanDate": "2025-09-20",
    "dueDate": "2025-09-15",
    "returnDate": "2025-09-20",
    "loanStatus": "RETURNED",
    "renewals": 0
  }
}
```
</details>

--- 
### ⚙️ Tecnologias utilizadas

* **Java 21**
* **Spring Boot** (Web, Data JPA, Security)
* **JWT** para autenticação/autorização
* **PostgreSQL**
* **Flyway** para ORM
* **Maven**
* **Swagger/OpenAPI** para documentação dos endpoints
---

## ▶️ Como rodar o projeto localmente

### 1. Pré-requisitos

- **Java 21+**
- **Maven 3.8+**
- **PostgreSQL**
- (Opcional) **IntelliJ IDEA** ou **VSCode**

### 2. Configuração do banco de dados

Configure o banco de dados no arquivo `src/main/resources/application.properties` com suas credenciais locais:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/library_api
spring.datasource.username=root
spring.datasource.password=1234
```

> 💡 Certifique-se de que o banco já existe antes de iniciar a aplicação.

### 3. Rodando via terminal (Maven)

```bash
# Baixar dependências e compilar o projeto
mvn clean install

# Rodar a aplicação
mvn spring-boot:run
```

### 4. Rodando via IntelliJ

1. Abra o projeto no IntelliJ
2. Aguarde o carregamento do Maven
3. Navegue até a classe `LibraryApiApplication.java`
4. Clique com o botão direito e selecione **Run 'LibraryApiApplication'**


---
## 📄 Documentação

Após rodar a aplicação:

Acesse http://localhost:8080/swagger-ui.html para explorar a documentação interativa da API com Swagger.

---


