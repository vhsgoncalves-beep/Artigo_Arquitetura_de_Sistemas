import axios from 'axios'

const api = axios.create({
  baseURL: 'https://artigoarquiteturadesistemas-production.up.railway.app/api',
})

export const listarLivros = () => api.get('/livros')
export const cadastrarLivro = (dados) => api.post('/livros', dados)
export const listarEmprestimos = () => api.get('/emprestimos')
export const realizarEmprestimo = (dados) => api.post('/emprestimos', dados)
export const devolverLivro = (emprestimoId) =>
  api.put(`/emprestimos/${emprestimoId}/devolucao`)
