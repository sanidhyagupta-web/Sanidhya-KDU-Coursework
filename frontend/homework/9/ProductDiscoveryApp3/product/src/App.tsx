import { Routes, Route } from 'react-router-dom'
import Home from './pages/Home'
import Product from './pages/Product'
import Header from './Components/Header'
import ProductSearch from './pages/ProductSearch'
import { Provider } from "react-redux";
import { store } from './thunks/store'
import Cart from './pages/Cart';


function App() {

  return (
    <Provider store={store}>
      <Header/>
      <Routes>
        <Route path="/" element={<Home/>}></Route>
        <Route path="/products/:id" element={<Product/>}></Route>
        <Route path="/product/search" element={<ProductSearch/>}> </Route>
        <Route path="/cart" element={<Cart />} />
      </Routes>
    </Provider>
  )
}

export default App
