import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { cadastrarLivro } from '../api/api.js'
import LivroForm from '../components/LivroForm.jsx'

/**
 * Página de cadastro de novo livro.
 * Após sucesso, redireciona para o acervo.
 */
export default function CadastrarLivro() {
  const [loading, setLoading] = useState(false)
  const [erro, setErro] = useState(null)
  const [sucesso, setSucesso] = useState(null)
  const navigate = useNavigate()

  const handleSubmit = async (dados) => {
    try {
      setLoading(true)
      setErro(null)
      await cadastrarLivro(dados)
      setSucesso(`Livro "${dados.titulo}" cadastrado com sucesso!`)
      setTimeout(() => navigate('/livros'), 1500)
    } catch (err) {
      setErro(err.response?.data?.erro || 'Erro ao cadastrar livro.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="page-form">
      <h1 className="page-title">➕ Cadastrar Livro</h1>

      {erro && <div className="alerta alerta-erro">{erro}</div>}
      {sucesso && <div className="alerta alerta-sucesso">{sucesso}</div>}

      <div className="card">
        <LivroForm onSubmit={handleSubmit} loading={loading} />
      </div>
    </div>
  )
}
