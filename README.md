## 📚 Library Api

A **Library Api** é uma aplicação desenvolvida em **Java com Spring Boot** que tem como objetivo gerenciar o funcionamento de uma biblioteca digital/física.
Ela permite o **cadastro de usuários, autores, livros e exemplares**, além de controlar **empréstimos, devoluções e multas**.

O projeto também implementa **autenticação e autorização via JWT**, garantindo que apenas usuários autorizados possam acessar determinadas funcionalidades.

Exemplo: um **usuário comum** pode realizar empréstimos e reservas, enquanto um **administrador** pode cadastrar novos livros, gerenciar exemplares e aplicar multas.

---

### 🎯 Objetivos principais

* Gerenciar os **usuários** da biblioteca (leitores e administradores).
* Permitir o cadastro de **livros, autores e exemplares**.
* Controlar **empréstimos** e **devoluções** com prazos definidos.
* Calcular e registrar **multas** em caso de atraso.
* Implementar **autenticação e autorização** para proteger os endpoints.
* Disponibilizar relatórios úteis, como:

    * livros mais emprestados,
    * usuários com mais atrasos,
    * autores mais lidos.

---

### 🏗️ Entidades principais

* **Usuário** → cadastro de leitores e administradores, autenticação/autorização.
* **Autor** → dados dos autores dos livros.
* **Livro** → obra literária em si.
* **Exemplar** → cópia física/digital de um livro.
* **Empréstimo** → registro de retirada e devolução de exemplares.
* **Reserva** → fila de espera para livros emprestados.
* **Multa** → penalidade financeira por atraso.

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

### 🚀 Funcionalidades principais (endpoints)

* **Usuários:** cadastro, login, autenticação JWT.
* **Livros/Autores:** CRUD de obras e autores.
* **Exemplares:** cadastro e controle de disponibilidade.
* **Empréstimos:** realizar, consultar e devolver livros.
* **Multas:** geração e pagamento de multas.

---
