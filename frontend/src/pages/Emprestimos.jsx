import { useEffect, useState } from 'react'
import { listarEmprestimos, devolverLivro } from '../api/api.js'
import EmprestimoCard from '../components/EmprestimoCard.jsx'

/**
 * Página que lista todos os empréstimos (ativos e devolvidos)
 * e permite registrar devoluções.
 */
export default function Emprestimos() {
  const [emprestimos, setEmprestimos] = useState([])
  const [loading, setLoading] = useState(true)
  const [erro, setErro] = useState(null)
  const [sucesso, setSucesso] = useState(null)

  const carregar = async () => {
    try {
      setLoading(true)
      const res = await listarEmprestimos()
      setEmprestimos(res.data)
    } catch {
      setErro('Erro ao carregar empréstimos.')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { carregar() }, [])

  const handleDevolver = async (emprestimoId) => {
    try {
      setErro(null)
      await devolverLivro(emprestimoId)
      setSucesso('Livro devolvido com sucesso!')
      carregar()
    } catch (err) {
      setErro(err.response?.data?.erro || 'Erro ao devolver livro.')
    }
  }

  const ativos = emprestimos.filter((e) => !e.dataDevolucao)
  const devolvidos = emprestimos.filter((e) => e.dataDevolucao)

  return (
    <div>
      <h1 className="page-title">🔄 Empréstimos</h1>

      {erro && <div className="alerta alerta-erro">{erro}</div>}
      {sucesso && <div className="alerta alerta-sucesso">{sucesso}</div>}

      {loading ? (
        <p className="loading">Carregando...</p>
      ) : (
        <>
          <section>
            <h2 className="section-title">Em aberto ({ativos.length})</h2>
            {ativos.length === 0 ? (
              <p className="empty">Nenhum empréstimo ativo no momento.</p>
            ) : (
              <div className="cards-grid">
                {ativos.map((e) => (
                  <EmprestimoCard key={e.id} emprestimo={e} onDevolver={handleDevolver} />
                ))}
              </div>
            )}
          </section>

          {devolvidos.length > 0 && (
            <section style={{ marginTop: '2rem' }}>
              <h2 className="section-title">Histórico devolvidos ({devolvidos.length})</h2>
              <div className="cards-grid">
                {devolvidos.map((e) => (
                  <EmprestimoCard key={e.id} emprestimo={e} onDevolver={handleDevolver} />
                ))}
              </div>
            </section>
          )}
        </>
      )}
    </div>
  )
}
