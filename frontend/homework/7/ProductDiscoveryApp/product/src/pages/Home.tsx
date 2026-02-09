import React from "react";
import { useProducts } from "../hooks/useProduct";
import ProductCard from "../Components/ProductCard";
import "../styles/main.scss";


const Home = () => {
  const { data, loading, error, refetch } = useProducts();

  if (loading) return <p>Loading...</p>;

  if (error) {
    return (
      <div>
        <p>Error: {error}</p>
        <button onClick={refetch}>Retry</button>
      </div>
    );
  }

  return (
    <div className="product-grid">
      {data?.products.map((product) => (
        <ProductCard
          key={product.id}
          product={product}
        />
      ))}
    </div>
 
  );
};

export default Home;
