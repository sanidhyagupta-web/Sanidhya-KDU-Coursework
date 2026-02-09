import React from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { useProductsById } from '../hooks/useProduct';
import ProductDetailsView from '../Components/ProductDetailsView';

const Product = () => {

  const navigate = useNavigate();
  const {id} = useParams<{id : string}>();

  const productId = id ? Number(id) : undefined;
  const validProductId = Number.isFinite(productId) ? productId : undefined;

  const { data: product, loading, error, refetch } = useProductsById(validProductId);

  if (loading) return <p>Loading product...</p>;

    if (error) {
    return (
      <div>
        <p>Error: {error}</p>
        <button onClick={refetch}>Retry</button>
        <button onClick={() => navigate(-1)} style={{ marginLeft: 8 }}>
          Back
        </button>
      </div>
    );
  }

  if (!product) return <p>Product not found.</p>;


  return (
    <>
        <ProductDetailsView product={product}/>
    </>
  )
}

export default Product
