import React, { useEffect, useState } from "react";
import type { product } from "../types/product";
import { useNavigate } from "react-router-dom";
import "../styles/main.scss"

type ProductDetailsViewProps = {
  product: product;
};

const ProductDetailsView: React.FC<ProductDetailsViewProps> = ({ product }) => {
  const navigate = useNavigate();
  const [selectedImage, setSelectedImage] = useState<string | null>(null);

  // Set default image when product changes
  useEffect(() => {
    const first = product.thumbnail || product.images?.[0] || null;
    setSelectedImage(first);
  }, [product]);

  const images =
    product.images?.length
      ? product.images
      : product.thumbnail
        ? [product.thumbnail]
        : [];

  const mainImage = selectedImage ?? images[0];

  return (
    <div className="details">
      <button className="back-btn" onClick={()=> navigate(-1)}>
        ← Back
      </button>

      <div className="details-layout">
        <div className="gallery">
          {mainImage ? (
            <img
              className="main-image"
              src={mainImage}
              alt={product.title}
            />
          ) : (
            <div className="no-image">No image</div>
          )}

           {images.length > 1 && (
            <div className="thumbs">
              {images.map((img) => (
                <button
                  key={img}
                  type="button"
                  className={`thumb ${img === mainImage ? "active" : ""}`}
                  onClick={() => setSelectedImage(img)}
                >
                  <img src={img} alt={`${product.title} thumbnail`} />
                </button>
              ))}
            </div>
          )}
        </div>

        <div className="info">
          <h1>{product.title}</h1>
          <p className="desc">{product.description}</p>
          <p className="price">${product.price}</p>

          <div className="meta">
            {product.brand && (
              <div>
                <b>Brand:</b> {product.brand}
              </div>
            )}
            {product.category && (
              <div>
                <b>Category:</b> {product.category}
              </div>
            )}
            {product.rating != null && (
              <div>
                <b>Rating:</b> {product.rating}
              </div>
            )}
            {product.stock != null && (
              <div>
                <b>Stock:</b> {product.stock}
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetailsView;
