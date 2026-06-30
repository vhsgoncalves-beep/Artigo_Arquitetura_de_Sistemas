/**
 * Exibe os dados de um empréstimo e oferece botão de devolução
 * quando o livro ainda não foi devolvido.
 *
 * @param {Object}   props
 * @param {Object}   props.emprestimo   - Dados do empréstimo
 * @param {Function} props.onDevolver   - Callback acionado ao clicar em "Devolver"
 */
export default function EmprestimoCard({ emprestimo, onDevolver }) {
  const ativo = !emprestimo.dataDevolucao

  return (
    <div className={`card ${ativo ? '' : 'card-devolvido'}`}>
      <div className="card-header">
        <h3>{emprestimo.tituloLivro}</h3>
        <span className={`badge ${ativo ? 'badge-emprestado' : 'badge-disponivel'}`}>
          {ativo ? 'Em aberto' : 'Devolvido'}
        </span>
      </div>
      <p>👤 <strong>Usuário:</strong> {emprestimo.nomeUsuario}</p>
      <p>📅 <strong>Emprestado em:</strong> {emprestimo.dataEmprestimo}</p>
      {!ativo && (
        <p>✅ <strong>Devolvido em:</strong> {emprestimo.dataDevolucao}</p>
      )}
      {ativo && (
        <button className="btn btn-secondary" onClick={() => onDevolver(emprestimo.id)}>
          Devolver
        </button>
      )}
    </div>
  )
}
