import { useState } from 'react'

/**
 * Formulário controlado para cadastro de livro.
 *
 * @param {Object}   props
 * @param {Function} props.onSubmit  - Callback com os dados do formulário
 * @param {boolean}  props.loading   - Desabilita o botão durante requisição
 */
export default function LivroForm({ onSubmit, loading }) {
  const [form, setForm] = useState({ titulo: '', autor: '', isbn: '' })

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value })
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    onSubmit(form)
  }

  return (
    <form className="form" onSubmit={handleSubmit}>
      <div className="form-group">
        <label htmlFor="titulo">Título</label>
        <input
          id="titulo"
          name="titulo"
          type="text"
          placeholder="Ex: Clean Code"
          value={form.titulo}
          onChange={handleChange}
          required
        />
      </div>

      <div className="form-group">
        <label htmlFor="autor">Autor</label>
        <input
          id="autor"
          name="autor"
          type="text"
          placeholder="Ex: Robert C. Martin"
          value={form.autor}
          onChange={handleChange}
          required
        />
      </div>

      <div className="form-group">
        <label htmlFor="isbn">ISBN</label>
        <input
          id="isbn"
          name="isbn"
          type="text"
          placeholder="Ex: 978-0132350884"
          value={form.isbn}
          onChange={handleChange}
          required
        />
      </div>

      <button type="submit" className="btn btn-primary" disabled={loading}>
        {loading ? 'Cadastrando...' : 'Cadastrar Livro'}
      </button>
    </form>
  )
}
