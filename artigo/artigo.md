# Aplicação dos Princípios SOLID e Padrões de Projeto GoF em um Sistema de Biblioteca: Um Estudo de Caso com Refatoração Incremental

**Disciplina:** Engenharia de Software  
**Tecnologias:** Java 21, Spring Boot, React, Maven  

---

## Resumo

Este artigo apresenta o desenvolvimento de um sistema de gerenciamento de biblioteca como estudo de caso para a aplicação prática dos cinco princípios SOLID e de três padrões de projeto do catálogo Gang of Four (GoF): Factory Method, Facade e Observer. O trabalho parte de uma implementação inicial com problemas evidentes de acoplamento e coesão, demonstrando, por meio de uma refatoração incremental em quatro etapas (baby steps), como os princípios e padrões transformam o código em uma solução de alta coesão e baixo acoplamento. Os resultados evidenciam que a adoção sistemática dessas práticas reduz o custo de manutenção, aumenta a testabilidade e torna o sistema extensível sem modificações nas classes já existentes.

**Palavras-chave:** SOLID. Padrões de projeto. GoF. Refatoração. Engenharia de Software. Spring Boot.

---

## 1. Introdução

A produção de software de qualidade exige mais do que a simples satisfação dos requisitos funcionais. Atributos como manutenibilidade, extensibilidade e testabilidade são determinantes para a longevidade de um sistema no ambiente produtivo (PRESSMAN; MAXIM, 2016). Nesse contexto, os cinco princípios de design orientado a objetos agrupados sob o acrônimo SOLID, popularizados por Robert C. Martin (2003), e os 23 padrões de projeto catalogados por Gamma et al. (1994) constituem um conjunto consolidado de diretrizes para a construção de sistemas robustos.

Contudo, a aplicação isolada desses conceitos em exercícios teóricos nem sempre evidencia seus benefícios reais. Este trabalho propõe um estudo de caso concreto: o desenvolvimento de um sistema simples de biblioteca que cadastra livros, lista o acervo, realiza empréstimos e processa devoluções. O objetivo não é a quantidade de funcionalidades, mas a clareza na demonstração de como SOLID e GoF se complementam para produzir código limpo, coeso e de baixo acoplamento.

A metodologia adotada parte de uma implementação deliberadamente problemática, submetida a uma sequência de quatro refatorações incrementais, até atingir a versão final. Cada passo introduz um único conceito, tornando a evolução rastreável e didática.

---

## 2. Fundamentação Teórica

### 2.1 Princípios SOLID

Os princípios SOLID formam um conjunto de cinco diretrizes de design de classes orientadas a objetos. Individualmente, cada princípio resolve uma classe específica de problema; em conjunto, eles orientam a produção de software modular, flexível e com responsabilidades bem definidas (MARTIN, 2003).

#### 2.1.1 SRP — Single Responsibility Principle

O Princípio da Responsabilidade Única estabelece que uma classe deve ter apenas um motivo para mudar (MARTIN, 2003). Em termos práticos, cada classe deve ser responsável por um único aspecto do domínio. Uma classe que concentra regras de negócio, acesso a dados e formatação de saída possui três razões para mudar: alteração nas regras, mudança no banco de dados ou ajuste no formato de saída. Isso aumenta o risco de efeitos colaterais inesperados.

No sistema desenvolvido, o SRP é observado pela separação explícita de responsabilidades: `LivroService` contém apenas regras relativas a livros; `EmprestimoService`, apenas regras de empréstimo e devolução; `NotificationFactory`, apenas a criação de notificadores; `LibraryFacade`, apenas a orquestração das chamadas.

#### 2.1.2 OCP — Open/Closed Principle

O Princípio Aberto/Fechado determina que entidades de software devem estar abertas para extensão e fechadas para modificação (MEYER, 1988 apud MARTIN, 2003). Ou seja, ao adicionar um novo comportamento, o código existente não deve ser alterado, apenas estendido.

No sistema, esse princípio é aplicado na camada de notificações. A `NotificationFactory` decide qual implementação de `Notification` retornar com base em um tipo enumerado. A adição de um novo canal (por exemplo, SMS) requer apenas a criação de uma nova classe `SmsNotification` implementando a interface `Notification`, sem alterar qualquer service, factory ou observer existente.

#### 2.1.3 LSP — Liskov Substitution Principle

O Princípio da Substituição de Liskov afirma que objetos de uma subclasse devem poder substituir objetos de sua superclasse sem alterar o comportamento correto do programa (LISKOV; WING, 1994). Violações desse princípio geralmente se manifestam como verificações de tipo em tempo de execução (`instanceof`) ou comportamentos inconsistentes entre implementações.

No sistema, `EmailNotification` e `ConsoleNotification` implementam a interface `Notification` de forma completa e consistente. Qualquer componente que dependa de `Notification` funciona corretamente independentemente da implementação concreta utilizada, sem necessidade de conhecer os detalhes internos de cada uma.

#### 2.1.4 ISP — Interface Segregation Principle

O Princípio da Segregação de Interfaces estabelece que nenhum cliente deve ser forçado a depender de métodos que não utiliza (MARTIN, 2003). Interfaces volumosas criam acoplamentos desnecessários e dificultam a substituição de implementações.

No sistema, foram definidas interfaces específicas e enxutas: `BookService` expõe apenas operações de livro; `LoanService`, apenas operações de empréstimo; `Notification`, apenas o método `enviar()`; `EmprestimoObserver`, apenas `aoEmprestar()`. Nenhuma dessas interfaces acumula métodos de domínios distintos.

#### 2.1.5 DIP — Dependency Inversion Principle

O Princípio da Inversão de Dependência determina que módulos de alto nível não devem depender de módulos de baixo nível; ambos devem depender de abstrações (MARTIN, 2003). Além disso, abstrações não devem depender de detalhes; detalhes devem depender de abstrações.

No sistema, todas as dependências entre camadas são declaradas por meio de interfaces. O Spring Framework realiza a injeção de dependência via construtor, garantindo que nenhum componente de regra de negócio instancie diretamente uma implementação concreta com o operador `new`.

---

### 2.2 Padrões de Projeto GoF

Gamma et al. (1994) catalogaram 23 padrões de projeto divididos em três categorias: criacionais, estruturais e comportamentais. Este trabalho emprega um padrão de cada categoria para cobrir diferentes dimensões do design.

#### 2.2.1 Factory Method (Criacional)

O padrão Factory Method define uma interface para criação de um objeto, mas delega às subclasses a decisão de qual classe instanciar (GAMMA et al., 1994). No contexto do sistema, a `NotificationFactory` encapsula a lógica de seleção da implementação correta de `Notification` com base em um `NotificationType`. A utilização do Spring para injetar as implementações concretas no mapa interno da fábrica elimina qualquer uso de `new` na lógica de negócio e reforça o DIP.

#### 2.2.2 Facade (Estrutural)

O padrão Facade fornece uma interface unificada e simplificada para um conjunto de interfaces em um subsistema (GAMMA et al., 1994). Ele não adiciona funcionalidade, mas reduz a complexidade percebida pelo cliente. No sistema, a `LibraryFacade` apresenta ao controller uma interface simples com métodos como `borrowBook()` e `returnBook()`, ocultando a coordenação interna entre `LivroService` e `EmprestimoService` e a cadeia de notificações via Observer.

#### 2.2.3 Observer (Comportamental)

O padrão Observer define uma dependência um-para-muitos entre objetos, de modo que quando um objeto muda de estado, todos os seus dependentes são notificados e atualizados automaticamente (GAMMA et al., 1994). No sistema, `EmprestimoNotificador` implementa o papel de Subject, mantendo uma lista de `EmprestimoObserver`. Ao concluir um empréstimo, `EmprestimoService` invoca `notificarObservers()`, que aciona automaticamente `ConsoleObserver` e `EmailObserver`, sem que o service conheça os detalhes de nenhum deles.

---

## 3. Metodologia

O desenvolvimento seguiu uma abordagem de estudo de caso único, com as seguintes etapas:

1. **Definição de escopo mínimo:** quatro funcionalidades (cadastrar livro, listar livros, emprestar livro, devolver livro) para manter o sistema didático e explicável.
2. **Implementação inicial deliberadamente problemática:** criação de uma classe monolítica com responsabilidades misturadas, acoplamento direto, uso excessivo de `new` e estruturas `if/else` para seleção de comportamento.
3. **Refatoração incremental em quatro passos:** cada passo introduz um único conceito de SOLID ou GoF, preservando o comportamento externo do sistema (refatoração semântica).
4. **Implementação final:** código resultante validado contra os cinco princípios SOLID e os três padrões GoF escolhidos.
5. **Documentação e diagramação:** geração de diagramas UML (PlantUML) e elaboração deste artigo.

O backend foi implementado em Java 21 com Spring Boot 3.3, utilizando Spring Data JPA sobre banco H2 em memória, Lombok para redução de boilerplate e Jakarta Validation para validação de entrada. O frontend foi desenvolvido em React 18 com Vite 5, consumindo a API REST via Axios.

---

## 4. Estudo de Caso

### 4.1 Problema Inicial

A implementação inicial concentrava em uma única classe (`EmprestimoServiceRuim`) as responsabilidades de acesso a dados, regras de negócio e envio de notificações. Essa classe apresentava os seguintes problemas:

- **Alto acoplamento:** instanciava diretamente `EmailSender` com `new`, criando dependência rígida sobre uma implementação concreta.
- **Estruturas condicionais para seleção de comportamento:** uma cadeia de `if/else` decidia qual tipo de notificação usar, violando o OCP (qualquer novo tipo exigiria modificação na classe).
- **Responsabilidades misturadas:** a mesma classe realizava consultas ao banco, aplicava regras de negócio e enviava notificações, violando o SRP.
- **Dificuldade de teste:** sem injeção de dependência, era impossível substituir o `EmailSender` por um dublê de teste.

### 4.2 Código Original (síntese)

```java
// Código problemático — para fins didáticos
public class EmprestimoServiceRuim {
    public void emprestar(Long livroId, String usuario, String tipoNotificacao) {
        Livro livro = BancoFake.buscarLivro(livroId);     // acoplamento direto ao banco
        if (!livro.disponivel) { return; }
        livro.disponivel = false;
        BancoFake.salvar(livro);                          // persistência misturada

        Emprestimo e = new Emprestimo();                  // criação manual com new
        BancoFake.salvarEmprestimo(e);

        if (tipoNotificacao.equals("EMAIL")) {            // if/else que viola OCP
            new EmailSender().enviar(usuario, "...");     // new direto (viola DIP)
        } else if (tipoNotificacao.equals("CONSOLE")) {
            System.out.println("...");
        } else if (tipoNotificacao.equals("SMS")) {
            // necessita alterar esta classe para adicionar SMS
        }
    }
}
```

### 4.3 Processo de Refatoração

#### Passo 1 — Extração da Persistência para Repository

A primeira refatoração introduziu interfaces `LivroRepository` e `EmprestimoRepository`, removendo o acesso direto ao banco de dentro do service. O service passou a receber essas dependências via construtor (DIP em ação), tornando-se testável com implementações em memória.

**Resultado:** violação de SRP parcialmente corrigida. Ainda restavam: `new EmailSender()` e `if/else` de notificação.

#### Passo 2 — Introdução da Interface `Notification` (LSP + DIP)

A segunda refatoração criou a interface `Notification` com o método `enviar(String destinatario, String mensagem)`. `EmailNotification` e `ConsoleNotification` foram implementadas como classes concretas separadas, injetadas no service por construtor.

**Resultado:** o service passou a depender de abstrações (DIP). As implementações tornaram-se substituíveis (LSP). Ainda restava o `if/else` para decidir qual usar.

#### Passo 3 — Introdução da `NotificationFactory` (Factory Method + OCP)

A terceira refatoração extraiu o `if/else` de notificação para a `NotificationFactory`. O service passou a receber apenas a fábrica, delegando a ela a decisão de qual `Notification` retornar. A adição de um novo canal não requer mais alteração no service.

**Resultado:** OCP plenamente aplicado. Factory Method funcionando. `if/else` eliminado da regra de negócio.

#### Passo 4 — Introdução do Observer e da Facade (código final)

A quarta refatoração desacoplou completamente a notificação do empréstimo por meio do padrão Observer. O service passou a chamar apenas `emprestimoSubject.notificarObservers(emprestimo)`, sem conhecer nenhum observer concreto. Em paralelo, a `LibraryFacade` foi criada para orquestrar `LivroService` e `EmprestimoService`, simplificando os controllers.

**Resultado:** sistema completamente refatorado, aplicando os cinco princípios SOLID e os três padrões GoF.

### 4.4 Código Final (síntese)

```java
// EmprestimoService — responsabilidade única, depende de abstrações
@Service
public class EmprestimoService implements LoanService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroService livroService;
    private final EmprestimoSubject emprestimoSubject; // abstração (DIP)

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                              LivroService livroService,
                              EmprestimoSubject emprestimoSubject) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroService = livroService;
        this.emprestimoSubject = emprestimoSubject;
    }

    @Override
    public EmprestimoDTO emprestar(EmprestimoRequestDTO request) {
        Livro livro = livroService.buscarEntidadePorId(request.getLivroId());
        if (!livro.isDisponivel()) throw new LivroIndisponivelException(livro.getId());

        livro.setDisponivel(false);
        livroService.salvar(livro);

        Emprestimo emprestimo = Emprestimo.builder()
                .livro(livro).nomeUsuario(request.getNomeUsuario())
                .dataEmprestimo(LocalDate.now()).build();

        Emprestimo salvo = emprestimoRepository.save(emprestimo);
        emprestimoSubject.notificarObservers(salvo); // Observer: sem conhecer detalhes
        return EmprestimoDTO.fromEntity(salvo);
    }
}
```

```java
// NotificationFactory — Factory Method, aplica OCP
@Component
public class NotificationFactory {
    private final Map<NotificationType, Notification> implementacoes;

    public NotificationFactory(EmailNotification email, ConsoleNotification console) {
        this.implementacoes = Map.of(
            NotificationType.EMAIL, email,
            NotificationType.CONSOLE, console
        );
    }

    public Notification criar(NotificationType tipo) {
        return implementacoes.get(tipo); // sem if/else
    }
}
```

---

## 5. Diagramas UML

Os diagramas a seguir foram gerados com PlantUML e estão disponíveis na pasta `uml/` do repositório.

### 5.1 Diagrama de Casos de Uso

O sistema possui quatro casos de uso principais, todos acessíveis pelo Usuário da Biblioteca: Cadastrar Livro, Listar Livros, Emprestar Livro e Devolver Livro. O caso de uso Emprestar Livro inclui automaticamente as notificações via Console e via E-mail (relacionamento `<<include>>`), disparadas pelo padrão Observer sem intervenção direta do usuário.

### 5.2 Diagrama de Classes

O diagrama de classes revela a estrutura do sistema em camadas, com destaque para:
- As interfaces `BookService`, `LoanService`, `Notification`, `EmprestimoObserver` e `EmprestimoSubject`, que formam as abstrações centrais (ISP + DIP).
- A `LibraryFacade` como ponto único de acesso para os controllers (Facade).
- A `NotificationFactory` conectando o enum `NotificationType` às implementações concretas (Factory Method).
- A relação de composição entre `EmprestimoNotificador` e os observers (Observer).

### 5.3 Diagrama de Sequência — Realizar Empréstimo

O diagrama de sequência do caso de uso "Emprestar Livro" evidencia o fluxo completo:

1. O controller recebe a requisição HTTP POST e delega à `LibraryFacade`.
2. A Facade delega ao `EmprestimoService`.
3. O service consulta o livro via `LivroService`, verifica disponibilidade, persiste o empréstimo.
4. O service invoca `EmprestimoNotificador.notificarObservers()`.
5. O notificador aciona `ConsoleObserver` e `EmailObserver` sequencialmente.
6. Cada observer solicita à `NotificationFactory` a implementação correta e delega o envio.
7. A resposta sobe pela pilha de volta ao controller, que retorna `201 Created`.

---

## 6. Resultados

### 6.1 Qualidade Externa

O sistema entrega as quatro funcionalidades solicitadas (cadastrar, listar, emprestar, devolver) por meio de uma API REST bem definida, consumida pelo frontend React. As respostas seguem o formato JSON, com códigos HTTP adequados para cada situação (200, 201, 404, 409).

### 6.2 Qualidade Interna

#### Alta Coesão
Cada classe possui responsabilidade única e bem definida. `LivroService` não sabe da existência de observers; `EmprestimoNotificador` não conhece regras de empréstimo; `NotificationFactory` não sabe quando notificar — apenas como criar o notificador correto.

#### Baixo Acoplamento
As dependências entre componentes são sempre declaradas por meio de interfaces. A substituição de qualquer implementação (por exemplo, trocar `EmailNotification` por uma versão que usa SMTP real) não exige alteração em nenhuma outra classe.

#### Extensibilidade
Para adicionar um novo canal de notificação (ex.: push notification), o desenvolvedor precisa:
1. Criar `PushNotification implements Notification`
2. Criar `PushObserver implements EmprestimoObserver`
3. Adicionar `NotificationType.PUSH` ao enum
4. Registrar `PUSH → PushNotification` na `NotificationFactory`

Nenhuma classe existente precisa ser modificada, o que demonstra a aplicação efetiva do OCP.

#### Testabilidade
A injeção de dependência via construtor permite que qualquer componente seja testado de forma isolada com mocks. O `EmprestimoService` pode ser testado injetando mocks de `LivroService`, `EmprestimoRepository` e `EmprestimoSubject`, sem necessidade de banco de dados real.

### 6.3 Comparativo Antes × Depois

| Critério | Antes | Depois |
|---|---|---|
| Responsabilidades por classe | Múltiplas | Uma |
| Dependência de implementações concretas | Direta (`new`) | Via interfaces (DIP) |
| Adição de novo tipo de notificação | Requer modificação | Apenas extensão (OCP) |
| Estruturas `if/else` para comportamento | Presentes | Eliminadas (Factory Method) |
| Acoplamento entre empréstimo e notificação | Forte | Desacoplado (Observer) |
| Testabilidade | Baixa | Alta |

---

## 7. Conclusão

Este trabalho demonstrou, por meio de um estudo de caso com escopo deliberadamente reduzido, que os princípios SOLID e os padrões de projeto GoF não são conceitos independentes: eles se reforçam mutuamente. O DIP cria o solo fértil para o Factory Method e o Observer florescerem. O SRP torna cada classe substituível, viabilizando o LSP. O OCP é a consequência natural de um design bem estruturado pelas demais práticas.

A metodologia de refatoração incremental (baby steps) mostrou-se especialmente eficaz para evidenciar a contribuição de cada conceito isoladamente, tornando o processo de aprendizado rastreável. Cada passo preservou o comportamento externo do sistema enquanto melhorava sua estrutura interna, o que é a essência da refatoração (FOWLER, 2018).

Como trabalho futuro, seria possível expandir o sistema com autenticação, testes automatizados (unitários e de integração) e o padrão Strategy para diferentes políticas de prazo de empréstimo — tudo isso sem alterar as classes já desenvolvidas, evidenciando que a arquitetura construída está genuinamente preparada para crescer.

---

## Referências

FOWLER, M. **Refactoring: Improving the Design of Existing Code**. 2. ed. Boston: Addison-Wesley, 2018.

GAMMA, E. et al. **Design Patterns: Elements of Reusable Object-Oriented Software**. Boston: Addison-Wesley, 1994.

LISKOV, B.; WING, J. A behavioral notion of subtyping. **ACM Transactions on Programming Languages and Systems**, v. 16, n. 6, p. 1811–1841, nov. 1994.

MARTIN, R. C. **Agile Software Development: Principles, Patterns, and Practices**. Upper Saddle River: Prentice Hall, 2003.

MARTIN, R. C. **Clean Code: A Handbook of Agile Software Craftsmanship**. Upper Saddle River: Prentice Hall, 2009.

PRESSMAN, R. S.; MAXIM, B. R. **Engenharia de Software: Uma Abordagem Profissional**. 8. ed. Porto Alegre: AMGH, 2016.

WALLS, C. **Spring in Action**. 6. ed. Shelter Island: Manning Publications, 2022.
