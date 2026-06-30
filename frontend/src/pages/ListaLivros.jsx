import { useEffect, useState } from 'react'
import { listarLivros, realizarEmprestimo } from '../api/api.js'
import LivroCard from '../components/LivroCard.jsx'

/**
 * Página que exibe todos os livros do acervo e permite iniciar
 * um empréstimo a partir de um modal simples.
 */
export default function ListaLivros() {
  const [livros, setLivros] = useState([])
  const [loading, setLoading] = useState(true)
  const [erro, setErro] = useState(null)
  const [sucesso, setSucesso] = useState(null)

  // Modal de empréstimo
  const [modalAberto, setModalAberto] = useState(false)
  const [livroSelecionado, setLivroSelecionado] = useState(null)
  const [nomeUsuario, setNomeUsuario] = useState('')
  const [emprestando, setEmprestando] = useState(false)

  const carregar = async () => {
    try {
      setLoading(true)
      const res = await listarLivros()
      setLivros(res.data)
    } catch {
      setErro('Erro ao carregar livros. Verifique se o backend está rodando.')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { carregar() }, [])

  const abrirModal = (livro) => {
    setLivroSelecionado(livro)
    setNomeUsuario('')
    setModalAberto(true)
    setErro(null)
    setSucesso(null)
  }

  const fecharModal = () => {
    setModalAberto(false)
    setLivroSelecionado(null)
  }

  const confirmarEmprestimo = async () => {
    if (!nomeUsuario.trim()) return
    try {
      setEmprestando(true)
      await realizarEmprestimo({ livroId: livroSelecionado.id, nomeUsuario })
      setSucesso(`Livro "${livroSelecionado.titulo}" emprestado para ${nomeUsuario}!`)
      fecharModal()
      carregar()
    } catch (err) {
      setErro(err.response?.data?.erro || 'Erro ao realizar empréstimo.')
    } finally {
      setEmprestando(false)
    }
  }

  return (
    <div>
      <h1 className="page-title">📖 Acervo de Livros</h1>

      {erro && <div className="alerta alerta-erro">{erro}</div>}
      {sucesso && <div className="alerta alerta-sucesso">{sucesso}</div>}

      {loading ? (
        <p className="loading">Carregando...</p>
      ) : livros.length === 0 ? (
        <p className="empty">Nenhum livro cadastrado ainda.</p>
      ) : (
        <div className="cards-grid">
          {livros.map((livro) => (
            <LivroCard key={livro.id} livro={livro} onEmprestar={abrirModal} />
          ))}
        </div>
      )}

      {/* Modal de empréstimo */}
      {modalAberto && (
        <div className="modal-overlay" onClick={fecharModal}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <h2>Emprestar livro</h2>
            <p><strong>{livroSelecionado?.titulo}</strong></p>
            <div className="form-group">
              <label htmlFor="usuario">Nome do usuário</label>
              <input
                id="usuario"
                type="text"
                placeholder="Ex: João Silva"
                value={nomeUsuario}
                onChange={(e) => setNomeUsuario(e.target.value)}
                autoFocus
              />
            </div>
            <div className="modal-actions">
              <button className="btn btn-primary" onClick={confirmarEmprestimo} disabled={emprestando || !nomeUsuario.trim()}>
                {emprestando ? 'Emprestando...' : 'Confirmar'}
              </button>
              <button className="btn btn-ghost" onClick={fecharModal}>Cancelar</button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
