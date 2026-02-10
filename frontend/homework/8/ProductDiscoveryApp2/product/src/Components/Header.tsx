import React, { useEffect, useState, useRef } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import "../styles/main.scss"
import { useDebounce } from '../hooks/useDebounce'
import { useProductContext } from '../pages/Context'

const Header = () => {
  const [searchText, setSearchText] = useState("")
  const debouncedSearchText = useDebounce(searchText, 100)
  const navigate = useNavigate()
  const { setSearchQuery } = useProductContext()
  
  const handleSearch = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchText(e.target.value)
  }

  useEffect(() => {
    if (!debouncedSearchText.trim()) {
      setSearchQuery("")
      return
    }

    setSearchQuery(debouncedSearchText)
    navigate(`/product/search?q=${debouncedSearchText}`)
  }, [debouncedSearchText, navigate, setSearchQuery])

  return (
    <nav className="Header-top">
        <h1 className='header'>Product Discovery </h1>
        <ul>
            <input type="text" 
            placeholder='Search for products'
            className='search-bar'
            name='search-bar'
            value={searchText}
            onChange={handleSearch}
            />
            <button onClick={() => {
              setSearchText('')
              setSearchQuery('')
              navigate('/')
              return
            }} className='clear-btn'>
              X
            </button>
            <Link to="/" className="home-btn" onClick={() => {
              setSearchText('')
              setSearchQuery('')
            }}> Home </Link>
        </ul>
    </nav>
  )
}

export default Header
