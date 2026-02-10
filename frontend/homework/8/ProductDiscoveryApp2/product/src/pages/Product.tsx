import React, { useEffect } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { useProductContext } from '../pages/Context';
import ProductDetailsView from '../Components/ProductDetailsView';

const Product = () => {

  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();
  const { selectedProduct, loading, error, fetchProductById } = useProductContext();

  useEffect(() => {
    if (id) {
      fetchProductById(id);
    }
  }, [id, fetchProductById]);

  if (loading) return <p>Loading product...</p>;

  if (error) {
    return (
      <div>
        <p>Error: {error}</p>
        <button onClick={() => id && fetchProductById(id)}>Retry</button>
        <button onClick={() => navigate(-1)} style={{ marginLeft: 8 }}>
          Back
        </button>
      </div>
    );
  }

  if (!selectedProduct) return <p>Product not found.</p>;


  return (
    <>
      <ProductDetailsView product={selectedProduct} />
    </>
  )
}

export default Product
