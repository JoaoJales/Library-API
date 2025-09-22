# 📜 Regras de Negócio – Library API 📚

Este documento descreve as **regras de negócio** implementadas no sistema **Library API**.  
Elas são aplicadas em cada operação para garantir o correto funcionamento dos processos da biblioteca.

> ⚠️ Importante: algumas regras **foram temporariamente relaxadas** para facilitar os testes e simulações. Ao final há uma seção que documenta essas mudanças

--- 
## 🔐 Autenticação
- Endpoints de cadastro e login **não exigem autenticação**.
- Todos os demais endpoints exigem autenticação com **token JWT**.
- O usuário realiza login via Email e senha e recebe um Token JWT.

---

## 👤 Usuários (Users)

- Cada usuário possui **role** definida: `VISITOR`, `STUDENT`, `PROFESSOR`, `ADMIN`.
- Usuários podem atualizar apenas seus próprios dados.
- Apenas administradores (`ADMIN`) podem listar todos os usuários.
- Senha deve seguir política mínima (mínimo de 8 caracteres, possuir pelo menos 1 letra e 1 número).
- Exclusão de usuário implica em inativação lógica (soft delete).

### Visitante (`Visitor`)

- Limitado a consultas do acervo
- ❌ Não realiza reservas e empréstimos 


---

## 📚 Livros, Cópias & Autores  (Books, BookCopies, Authors)

- Cada livro está associado a um único **autor**.
- Apenas administradores podem cadastrar, atualizar ou excluir livros/autores/cópias.
- Cada livro possui um ou mais **exemplares físicos**.
- Somente exemplares (cópias) **disponíveis** podem ser reservados ou emprestados.
- Caso todos os exemplares estejam emprestados, o livro pode ser **reservado**.

---


## 📖 Empréstimos (Loans)

- O número máximo de empréstimos simultâneos depende da role:
    - `STUDENT` → até **3 livros**
    - `PROFESSOR` → até **5 livros**
    - `ADMIN` → até **5 livros**
- Prazos de devolução:
    - `STUDENT` → **14 dias**
    - `PROFESSOR` → **30 dias**

- Empréstimos atrasados geram multa automaticamente no momento da devolução.

### 1. Criação de Empréstimos

- Um usuário não pode realizar um empréstimo se já atingiu o seu limite máximo
- Cópia do livro precisa estar disponível
- O empréstimo será **negado** se o usuário:
    - Tiver multas não pagas
    - Tiver empréstimos atrasados
    - Estiver inativo no sistema
    - Já possuir um empréstimo do livro

### 2. Renovação de Empréstimos:
- Máximo de renovações permitidas: 2
- Renovação disponível nos últimos 3 dias de empréstimo _(desativado para testes)_
- A renovação será negada se:
    - O livro tiver reservas ativas na fila.
    - O empréstimo estiver no status LATE (atrasado).



### 3. Processamento de Reservas

- Após a devolução, o sistema verifica imediatamente a fila de reservas para o livro devolvido e processar a próxima reserva

---

## 📑 Reservas (Reservations)

As reservas gerenciam a fila de espera por um livro indisponível.

- **Usuários (`STUDENT`, `PROFESSOR`, `ADMIN`)**  podem criar uma reserva.
- ⚠️ **Usuários (`STUDENT`, `PROFESSOR`, `ADMIN`)**  podem confirmar a retirada de um livro reservado.

### 1. Criação de Reservas

- A reserva será negada se o usuário:
  - Tiver multas não pagas
  - Tiver empréstimos atrasados
- Limite por Livro:
  - Um usuário só pode ter uma reserva ativa/pronta (ACTIVE/FULFILLED) para um mesmo livro
  - Máximo de reservas totais por livro: 10

### 2. Fluxo

| Status        | Descrição                                              | Próxima Ação                |
|---------------|--------------------------------------------------------|-----------------------------|
| **ACTIVE**    | Reserva na fila, aguardando devolução de cópias.       | Livro devolvido → FULFILLED |
| **FULFILLED** | Cópia disponível. O usuário tem 2 dias para retirá-la. | Retirada → COMPLETED        |
| **COMPLETED** | Reserva transformada em um empréstimo ativo.           | Finalizado                  |
| **EXPIRED**   | A reserva não foi retirada no prazo de 2 dias.         | Finalizado                  |
| **CANCELLED** | Reserva cancelada pelo usuário ou pelo admin           | Finalizado                  |

##### Ao cancelar uma reserva o processamento da fila de reservas é disparado.  

---

## 💸 Multas (Fines)

- Multa aplicada por dia de atraso:
    - `STUDENT` → **R$ 1,50/dia**
    - `PROFESSOR` → **R$ 1,00/dia**
- Multa só é gerada quando o usuário devolve o exemplar em atraso.
- Usuário com multas **não pagas** não pode:
    - Realizar novas reservas.
    - Retirar novos empréstimos.
- ⚠️ Usuários (`STUDENT`, `PROFESSOR`, `ADMIN`) podem quitar multas manualmente.


---

## ⏲ Tarefas Agendadas (Scheduler)

### 1. Verificação de Atrasos: 
- Uma tarefa agendada roda diariamente à 00:00 AM para verificar e atualizar o loan.status de todos os empréstimos ativos cuja due_date (data de devolução) já passou para LATE (atrasado).

### 2. Verificação de Expiração de Reserva:
- Uma tarefa agendada roda para verificar e atualizar o reservation.status de todas as reservas que estavam FULFILLED e ultrapassaram o prazo de 2 dias para retirada para EXPIRED
  - Processamento da fila de reservas é disparado

>⚠️ As tarefa rodam a cada 1 minuto para testes

---

## 🕐 Horário de Funcionamento _(Desativado para testes)_

#### 1. Dias de funcionamento
   - Segunda a sábado → aberto.
   - Domingo → fechado.

#### 2. Horário de funcionamento
- Abertura: 08h00 
- Fechamento: 22h00

--- 
## 📊 Relatórios & Estatísticas (Admin)

### Relatórios de:
- Livros mais emprestados.
- Livros mais emprestados dentre um período.
- Maiores leitores (usuários com mais empréstimos feitos).
- Maiores autores (autores que possuem mais empréstimos registrados).
- Disponibilidade dos Livros
- Usuários com mais multas
- Usuários devedores (multas não pagas)
- Estatísticas de empréstimos para um ano específico.


---

# ⚠️ Regras Reais vs Regras de Teste

Durante os testes, algumas regras de negócio foram **liberadas** para permitir validar o fluxo completo sem precisar criar usuários e papéis diferentes o tempo todo:


- **Empréstimos**:
    - **Regra real** → Apenas administradores podem criar empréstimos e confirmar devoluções.
    - **Regra de teste** → O próprio usuário pode criar empréstimos e confirmar devoluções.

- **Reservas**:
    - **Regra real** → Apenas administradores podem confirmar a retirada de uma reserva feita por outro usuário.
    - **Regra de teste** → O próprio usuário também pode confirmar a retirada.

- **Multas**:
    - **Regra real** → Somente administradores podem marcar uma multa como paga.
    - **Regra de teste** → Qualquer usuário autenticado pode marcar como paga.




Essas flexibilizações são **temporárias** para testes e simulações e devem ser revertidas em produção.
