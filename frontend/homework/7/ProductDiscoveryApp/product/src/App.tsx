import { useState } from 'react'
import './App.css'
import { Routes,Route } from 'react-router-dom'
import Home from './pages/Home'
import Product from './pages/Product'
import Header from './Components/Header'

function App() {

  return (
    <>
      <Header/>
      <Routes>
        <Route path="/" element={<Home/>}></Route>
        <Route path="/product/:id" element={<Product/>}></Route>
      </Routes>
    </>
  )
}

export default App
