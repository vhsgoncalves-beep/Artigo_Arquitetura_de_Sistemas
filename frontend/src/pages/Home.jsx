import { Link } from 'react-router-dom'

/**
 * Página inicial com resumo do sistema e links rápidos.
 */
export default function Home() {
  return (
    <div className="page-home">
      <div className="hero">
        <h1>📚 Sistema de Biblioteca</h1>
        <p className="hero-subtitle">
          Estudo de caso acadêmico — princípios <strong>SOLID</strong> e padrões <strong>GoF</strong>
        </p>
      </div>

      <div className="cards-grid">
        <div className="card info-card">
          <h2>📖 Acervo</h2>
          <p>Consulte todos os livros cadastrados e veja quais estão disponíveis para empréstimo.</p>
          <Link to="/livros" className="btn btn-primary">Ver acervo</Link>
        </div>

        <div className="card info-card">
          <h2>➕ Cadastrar</h2>
          <p>Adicione novos títulos ao acervo informando título, autor e ISBN.</p>
          <Link to="/cadastrar" className="btn btn-primary">Cadastrar livro</Link>
        </div>

        <div className="card info-card">
          <h2>🔄 Empréstimos</h2>
          <p>Gerencie os empréstimos ativos e registre devoluções.</p>
          <Link to="/emprestimos" className="btn btn-primary">Ver empréstimos</Link>
        </div>
      </div>

      <div className="card info-tecnica">
        <h2>🏗️ Arquitetura aplicada</h2>
        <ul>
          <li><strong>SRP</strong> — LivroService, EmprestimoService e NotificationService com responsabilidades únicas</li>
          <li><strong>OCP</strong> — Novos tipos de notificação sem alterar código existente</li>
          <li><strong>LSP</strong> — EmailNotification e ConsoleNotification são substituíveis</li>
          <li><strong>ISP</strong> — Interfaces pequenas: BookService, LoanService, Notification</li>
          <li><strong>DIP</strong> — Services dependem de abstrações, injetadas pelo Spring</li>
          <li><strong>Factory Method</strong> — NotificationFactory decide a implementação em tempo de execução</li>
          <li><strong>Facade</strong> — LibraryFacade orquestra sem expor complexidade interna</li>
          <li><strong>Observer</strong> — Notificações automáticas ao realizar empréstimo</li>
        </ul>
      </div>
    </div>
  )
}
