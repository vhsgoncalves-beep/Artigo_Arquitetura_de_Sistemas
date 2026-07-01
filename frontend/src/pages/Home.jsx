import { Link } from 'react-router-dom'

/**
 * Página inicial com resumo do sistema e links rápidos.
 */
export default function Home() {
  return (
    <div className="page-home">
      <div className="hero">
        <h1>📚 Sistema de Biblioteca</h1>
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
    </div>
  )
}
