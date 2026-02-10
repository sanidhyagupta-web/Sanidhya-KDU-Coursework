import React, { useEffect } from 'react'
import { useNavigate, useSearchParams } from 'react-router-dom'
import { useProductContext } from '../pages/Context';
import ProductCard from '../Components/ProductCard';


const ProductSearch = () => {

  const navigate = useNavigate();
  const [searchParams] = useSearchParams()
  const query = searchParams.get('q') ?? undefined
  const { products, searchLoading, error, searchProducts } = useProductContext();

  useEffect(() => {
    searchProducts(query);
  }, [query, searchProducts]);

  if (searchLoading) {
    return <p>Loading Product...</p>
  }

  if (error) {
    return (
      <div>
        <p>Error: {error}</p>
        <button onClick={() => searchProducts(query)}>Retry</button>
        <button onClick={() => navigate(-1)} style={{ marginLeft: 8 }}>
          Back
        </button>
      </div>
    )
  }

  if (!products.length) return <p>Product not found</p>;
    
  return (
    <div className="product-grid">
      {products.map((product) => (
        <ProductCard
          key={product.id}
          product={product}
        />
      ))}
    </div>
  )
}

export default ProductSearch
