import { useQuery } from '@tanstack/react-query'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { getDrinks } from './api/drinks'
import Layout from './pages/Layout'
import Home from './pages/Home'
import NewDrink from './pages/NewDrink'
import EditDrink from './pages/EditDrink'

function App() {
  const getDrinksQuery = useQuery({
    queryKey: ['drinks'],
    queryFn: getDrinks,
  })

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home query={getDrinksQuery} />} />
          <Route path="new" element={<NewDrink />} />
          <Route
            path="edit/:id"
            element={<EditDrink query={getDrinksQuery} />}
          />
        </Route>
        <Route path="*" element={<p>404 Not found</p>} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
