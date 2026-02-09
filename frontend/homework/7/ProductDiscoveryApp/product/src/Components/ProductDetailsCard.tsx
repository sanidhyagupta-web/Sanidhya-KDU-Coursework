import React from 'react'
import type { product } from '../types/product'
import { Link } from 'react-router-dom'

type ProductDetailsProps = {
    product : product
}

const ProductDetailsCard : React.FC<ProductDetailsProps> = ({product}) => {
  return (
    <div>
    <Link to={`/product/${product.id}`} className="product-card-link">
      <div className="product-card">
        <img src={product.thumbnail} alt={product.title} />
        <h3>{product.title}</h3>
        <p>{product.description}</p>
        <strong>₹{product.price}</strong>
        {product.brand && <small>{product.brand}</small>}
      </div>
    </Link>

    </div>
  )
}

export default ProductDetailsCard
