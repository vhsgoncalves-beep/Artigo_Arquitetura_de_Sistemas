<<<<<<< HEAD
# 📚 Biblioteca SOLID + GoF

> Estudo de caso acadêmico — aplicação prática dos cinco princípios **SOLID** e dos padrões de projeto **GoF** (Factory Method, Facade e Observer) em um sistema de biblioteca.

---

## 📁 Estrutura do Projeto

```
biblioteca-solid-gof/
├── backend/          → API REST (Java 21 + Spring Boot)
├── frontend/         → Interface web (React + Vite)
├── uml/              → Diagramas PlantUML (.puml)
├── artigo/           → Artigo acadêmico em Markdown
├── refatoracao/      → Evolução em baby steps (código antes → depois)
└── README.md
```

---

## ⚙️ Como executar

### Backend

**Pré-requisitos:** Java 21, Maven 3.9+

```bash
cd backend
mvn spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

Console do H2 (banco em memória): `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:bibliotecadb`
- Usuário: `sa` | Senha: *(vazia)*

### Frontend

**Pré-requisitos:** Node.js 20+, npm

```bash
cd frontend
npm install
npm run dev
```

A interface ficará disponível em `http://localhost:5173`.

> O Vite faz proxy automático de `/api` para `http://localhost:8080`, portanto basta que o backend esteja rodando.

---

## 🔗 Endpoints da API

| Método | Endpoint                           | Descrição              |
|--------|------------------------------------|------------------------|
| GET    | `/api/livros`                      | Listar todos os livros |
| POST   | `/api/livros`                      | Cadastrar livro        |
| GET    | `/api/emprestimos`                 | Listar empréstimos     |
| POST   | `/api/emprestimos`                 | Realizar empréstimo    |
| PUT    | `/api/emprestimos/{id}/devolucao`  | Devolver livro         |

---

## 🏗️ Arquitetura

O sistema segue arquitetura em camadas, com fluxo unidirecional:

```
Controller → Facade → Service → Repository → Model
                                    ↕
                           Factory / Observer / Notification
```

### Camadas

| Camada        | Responsabilidade                                               |
|---------------|----------------------------------------------------------------|
| `controller`  | Receber requisições HTTP e devolver respostas                  |
| `facade`      | Orquestrar operações de alto nível (LibraryFacade)             |
| `service`     | Regras de negócio (LivroService, EmprestimoService)            |
| `repository`  | Persistência via Spring Data JPA                               |
| `model`       | Entidades JPA (Livro, Emprestimo)                              |
| `dto`         | Contratos de entrada e saída da API                            |
| `notification`| Abstração e implementações de notificação                      |
| `factory`     | Factory Method para criação de notificações                    |
| `observer`    | Observer para notificação automática no empréstimo             |
| `exception`   | Tratamento centralizado de erros                               |

---

## ✅ Princípios SOLID aplicados

### SRP — Single Responsibility Principle
Cada classe possui **uma única razão para mudar**:
- `LivroService` → regras de livro (cadastro, consulta)
- `EmprestimoService` → regras de empréstimo e devolução
- `NotificationFactory` → criação de notificações
- `LibraryFacade` → orquestração (sem lógica de negócio)
- `EmprestimoNotificador` → gerenciar e notificar observers

### OCP — Open/Closed Principle
O sistema está **aberto para extensão, fechado para modificação**:
- Para adicionar um novo canal de notificação (ex: SMS), basta criar uma nova classe implementando `Notification` e registrá-la na `NotificationFactory`. Nenhum service existente precisa ser alterado.

### LSP — Liskov Substitution Principle
`EmailNotification` e `ConsoleNotification` **substituem `Notification` sem quebrar** o contrato esperado. O restante do sistema depende somente da interface.

### ISP — Interface Segregation Principle
Interfaces **pequenas e coesas**, sem métodos que os clientes não usem:
- `BookService` → apenas operações de livro
- `LoanService` → apenas operações de empréstimo
- `Notification` → apenas `enviar()`
- `EmprestimoObserver` → apenas `aoEmprestar()`

### DIP — Dependency Inversion Principle
Módulos de alto nível **dependem de abstrações**, não de implementações:
- `EmprestimoService` depende de `EmprestimoSubject` (interface), não de `EmprestimoNotificador`
- `LibraryFacade` depende de `BookService` e `LoanService` (interfaces)
- Toda injeção é feita pelo Spring via construtor

---

## 🎨 Padrões GoF aplicados

### Factory Method
`NotificationFactory.criar(NotificationType tipo)` decide em **tempo de execução** qual implementação de `Notification` retornar, usando um mapa de implementações injetadas pelo Spring (sem `new` manual na lógica de negócio).

```
NotificationType.EMAIL   → EmailNotification
NotificationType.CONSOLE → ConsoleNotification
```

### Facade
`LibraryFacade` esconde a complexidade de coordenar `LivroService` e `EmprestimoService`. Os controllers conhecem apenas a Facade.

```
Controller → LibraryFacade → LivroService
                           → EmprestimoService → (Observer)
```

### Observer
`EmprestimoNotificador` (Subject) mantém uma lista de `EmprestimoObserver`. Ao persistir um empréstimo, `EmprestimoService` dispara `notificarObservers()`, que aciona automaticamente `ConsoleObserver` e `EmailObserver` — sem que o service conheça os detalhes de notificação.

---

## 🛠️ Tecnologias

| Camada    | Tecnologia                        |
|-----------|-----------------------------------|
| Backend   | Java 21, Spring Boot 3.3, Maven   |
| Banco     | H2 Database (em memória)          |
| ORM       | Spring Data JPA / Hibernate       |
| Validação | Jakarta Validation (Bean Validation) |
| Boilerplate | Lombok                          |
| Frontend  | React 18, Vite 5, React Router 6  |
| HTTP      | Axios                             |
| UML       | PlantUML                          |

---

## 📊 Diagramas UML

Os diagramas estão na pasta `uml/` em formato PlantUML (`.puml`).

Para renderizar, utilize:
- [PlantUML Online](https://www.plantuml.com/plantuml/uml/)
- Plugin PlantUML para IntelliJ IDEA
- VS Code com extensão PlantUML

| Arquivo                              | Conteúdo                              |
|--------------------------------------|---------------------------------------|
| `diagrama-classes.puml`              | Diagrama de classes completo          |
| `diagrama-sequencia-emprestimo.puml` | Sequência do fluxo de empréstimo      |
| `diagrama-casos-de-uso.puml`         | Casos de uso do sistema               |

---

## 📝 Artigo Acadêmico

Disponível em `artigo/artigo.md` com a estrutura completa:

1. Introdução  
2. Fundamentação Teórica (SOLID + GoF)  
3. Metodologia  
4. Estudo de Caso (código antes → refatoração → código final)  
5. Diagramas UML  
6. Resultados  
7. Conclusão  
8. Referências (ABNT)

---

## 🔄 Processo de Refatoração (Baby Steps)

A pasta `refatoracao/` contém a evolução do código em quatro passos:

| Passo | O que mudou |
|-------|-------------|
| `00-codigo-antes` | Código com alto acoplamento, `new` espalhado, `if/else` para notificação, responsabilidades misturadas |
| `01-passo1-repository` | Extração da persistência para Repository (separação de responsabilidades) |
| `02-passo2-interface-notification` | Introdução da interface `Notification` (DIP + LSP) |
| `03-passo3-factory-method` | Substituição do `if/else` pela `NotificationFactory` (OCP) |
| `04-passo4-observer-facade-final` | Introdução do Observer e da Facade — código final |
=======
# Artigo_Arquitetura_de_Sistemas
>>>>>>> 5061e59f8adffb619c3f43025afa52a2186e1249
