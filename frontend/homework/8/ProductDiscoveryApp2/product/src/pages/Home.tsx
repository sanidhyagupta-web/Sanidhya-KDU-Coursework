import React, { useEffect } from "react";
import { useProductContext } from "../pages/Context";
import ProductCard from "../Components/ProductCard";
import "../styles/main.scss";


const Home = () => {
  const { products, loading, error, fetchAllProducts } = useProductContext();

  useEffect(() => {
    fetchAllProducts();
  }, [fetchAllProducts]);

  if (loading) return <p>Loading...</p>;

  if (error) {
    return (
      <div>
        <p>Error: {error}</p>
        <button onClick={fetchAllProducts}>Retry</button>
      </div>
    );
  }

  return (
    <div className="product-grid">
      {products.map((product) => (
        <ProductCard
          key={product.id}
          product={product}
        />
      ))}
    </div>
  );
};

export default Home;
