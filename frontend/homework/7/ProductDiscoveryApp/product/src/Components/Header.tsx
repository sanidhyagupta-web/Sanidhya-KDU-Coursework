import React from 'react'
import { Link } from 'react-router-dom'
import "../styles/main.scss"

const Header = () => {
  

  return (
    <nav className="Header-top">
        <h1>Product Discovery</h1>
        <ul>
            <Link to="/" className="home-btn"> Home </Link>
        </ul>
    </nav>
  )
}

export default Header
