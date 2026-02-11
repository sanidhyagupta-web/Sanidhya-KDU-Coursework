import React, { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../hooks";
import { fetchProductsById } from "../thunks/product"; 
import ProductDetailsView from "../Components/ProductDetailsView";

const Product = () => {
  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();

  const dispatch = useAppDispatch();
  const { Selectedproduct, Loadingstate, errorstate } = useAppSelector(
    (s) => s.products
  );

  useEffect(() => {
    if (id) {
      dispatch(fetchProductsById(id));
    }
  }, [id, dispatch]);

  if (Loadingstate) return <p>Loading product...</p>;

  if (errorstate) {
    return (
      <div>
        <p>Error: {errorstate}</p>
        <button onClick={() => id && dispatch(fetchProductsById(id))}>
          Retry
        </button>
        <button onClick={() => navigate(-1)} style={{ marginLeft: 8 }}>
          Back
        </button>
      </div>
    );
  }

  if (!Selectedproduct) return <p>Product not found.</p>;

  return <ProductDetailsView product={Selectedproduct} />;
};

export default Product;
