import React, { useEffect } from "react";
import { useAppDispatch, useAppSelector } from "../hooks";
import { fetchAllProducts } from "../thunks/product"; // adjust path if needed
import ProductCard from "../Components/ProductCard";
import "../styles/main.scss";

const Home = () => {
  const dispatch = useAppDispatch();
  const { productList, Loadingstate, errorstate } = useAppSelector(
    (s) => s.products
  );

  useEffect(() => {
    if (productList.length === 0) {
      dispatch(fetchAllProducts());
    }
  }, [dispatch, productList.length]);

  if (Loadingstate) return <p>Loading...</p>;

  if (errorstate) {
    return (
      <div>
        <p>Error: {errorstate}</p>
        <button onClick={() => dispatch(fetchAllProducts())}>Retry</button>
      </div>
    );
  }

  return (
    <div className="product-grid">
      {productList.map((product) => (
        <ProductCard key={product.id} product={product} />
      ))}
    </div>
  );
};

export default Home;
