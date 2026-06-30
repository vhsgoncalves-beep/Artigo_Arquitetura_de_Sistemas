import { NavLink } from 'react-router-dom'

/**
 * Barra de navegação global presente em todas as páginas.
 */
export default function Navbar() {
  return (
    <nav className="navbar">
      <span className="navbar-brand">📚 Biblioteca SOLID</span>
      <div className="navbar-links">
        <NavLink to="/" end className={({ isActive }) => isActive ? 'active' : ''}>
          Home
        </NavLink>
        <NavLink to="/livros" className={({ isActive }) => isActive ? 'active' : ''}>
          Acervo
        </NavLink>
        <NavLink to="/cadastrar" className={({ isActive }) => isActive ? 'active' : ''}>
          Cadastrar Livro
        </NavLink>
        <NavLink to="/emprestimos" className={({ isActive }) => isActive ? 'active' : ''}>
          Empréstimos
        </NavLink>
      </div>
    </nav>
  )
}
