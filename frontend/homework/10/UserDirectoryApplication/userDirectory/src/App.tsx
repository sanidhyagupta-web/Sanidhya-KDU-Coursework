import { useState } from 'react'

import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Header from './Components/Header'
import Home from './Components/Home'
import ProductDetails from './Components/ProductDetails'

function App() {

  return (
    <>
      <BrowserRouter>
          <Routes>
              <Route path='/' element={<Home/>}></Route>
              <Route path='/id/:id' element={<ProductDetails/>}></Route>
          </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
