import { Routes, Route } from 'react-router-dom'
import Home from './pages/Home'
import Product from './pages/Product'
import Header from './Components/Header'
import ProductSearch from './pages/ProductSearch'
import ProductProvider from './pages/Context'

function App() {

  return (
    <ProductProvider>
      <Header/>
      <Routes>
        <Route path="/" element={<Home/>}></Route>
        <Route path="/products/:id" element={<Product/>}></Route>
        <Route path="/product/search" element={<ProductSearch/>}> </Route>
      </Routes>
    </ProductProvider>
  )
}

export default App
