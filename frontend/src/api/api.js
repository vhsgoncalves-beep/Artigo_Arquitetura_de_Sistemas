import axios from 'axios'

/**
 * Instância centralizada do Axios.
 * O Vite faz proxy de /api para http://localhost:8080/api (vite.config.js).
 */
const api = axios.create({
  baseURL: 'artigoarquiteturadesistemas-production.up.railway.app',
})

// ── Livros ──────────────────────────────────────────────────
export const listarLivros = () => api.get('/livros')

export const cadastrarLivro = (dados) => api.post('/livros', dados)

// ── Empréstimos ─────────────────────────────────────────────
export const listarEmprestimos = () => api.get('/emprestimos')

export const realizarEmprestimo = (dados) => api.post('/emprestimos', dados)

export const devolverLivro = (emprestimoId) =>
  api.put(`/emprestimos/${emprestimoId}/devolucao`)
