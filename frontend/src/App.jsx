import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Navbar from './components/Navbar.jsx'
import Home from './pages/Home.jsx'
import ListaLivros from './pages/ListaLivros.jsx'
import CadastrarLivro from './pages/CadastrarLivro.jsx'
import Emprestimos from './pages/Emprestimos.jsx'

export default function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <main className="container">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/livros" element={<ListaLivros />} />
          <Route path="/cadastrar" element={<CadastrarLivro />} />
          <Route path="/emprestimos" element={<Emprestimos />} />
        </Routes>
      </main>
    </BrowserRouter>
  )
}
