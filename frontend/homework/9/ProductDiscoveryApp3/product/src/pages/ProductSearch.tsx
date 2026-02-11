import React, { useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../hooks";
import { searchProduct } from "../thunks/product"; // adjust path if needed
import ProductCard from "../Components/ProductCard";

const ProductSearch = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const query = searchParams.get("q") ?? "";

  const dispatch = useAppDispatch();
  const { productList, Searchloadingstate, errorstate } = useAppSelector(
    (s) => s.products
  );

  useEffect(() => {
    const q = query.trim();
    if (q) {
      dispatch(searchProduct(q));
    }
  }, [query, dispatch]);

  if (Searchloadingstate) {
    return <p>Loading Product...</p>;
  }

  if (errorstate) {
    return (
      <div>
        <p>Error: {errorstate}</p>
        <button onClick={() => dispatch(searchProduct(query))}>Retry</button>
        <button onClick={() => navigate(-1)} style={{ marginLeft: 8 }}>
          Back
        </button>
      </div>
    );
  }

  if (!query.trim()) return <p>Type something to search</p>;
  if (!productList.length) return <p>Product not found</p>;

  return (
    <div className="product-grid">
      {productList.map((product) => (
        <ProductCard key={product.id} product={product} />
      ))}
    </div>
  );
};

export default ProductSearch;
