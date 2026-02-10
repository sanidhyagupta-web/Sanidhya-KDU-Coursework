import React from 'react'
import type { product } from '../types/product'
import "../styles/main.scss"
import { useNavigate } from 'react-router-dom';


type ProductCardProps = {
    product : product
}

const ProductCard : React.FC<ProductCardProps> = ({product}) => {
  const navigate = useNavigate()

  const handleClick = ()=> {
        navigate(`/products/${product.id}`)
  }

  return (
    <div className='product-card' onClick={handleClick} role='button'>
        <h2>{product.title}</h2>
        <h3>{product.category}</h3>
        <img src={product.thumbnail} 
             alt="Image not available" />
        <p>{product.description}</p>
        <p>
            <strong className='card-container'>Price : {product.price}</strong>
          
         
        </p>
        <strong className='card-container'>{product.brand}</strong>
        <h4 className='card-container'>Rating : {product.rating}/5</h4>
        <h4 className='card-container'>Stock : {product.stock}</h4>

    </div>

  )
}

export default ProductCard
