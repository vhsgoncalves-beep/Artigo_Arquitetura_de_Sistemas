/**
 * Exibe as informações de um livro e, quando indisponível,
 * mostra o badge de "Emprestado".
 *
 * @param {Object}   props
 * @param {Object}   props.livro        - Dados do livro
 * @param {Function} props.onEmprestar  - Callback acionado ao clicar em "Emprestar"
 */
export default function LivroCard({ livro, onEmprestar }) {
  return (
    <div className={`card ${!livro.disponivel ? 'card-indisponivel' : ''}`}>
      <div className="card-header">
        <h3>{livro.titulo}</h3>
        <span className={`badge ${livro.disponivel ? 'badge-disponivel' : 'badge-emprestado'}`}>
          {livro.disponivel ? 'Disponível' : 'Emprestado'}
        </span>
      </div>
      <p className="card-autor">✍️ {livro.autor}</p>
      <p className="card-isbn">ISBN: {livro.isbn}</p>
      {livro.disponivel && onEmprestar && (
        <button className="btn btn-primary" onClick={() => onEmprestar(livro)}>
          Emprestar
        </button>
      )}
    </div>
  )
}
