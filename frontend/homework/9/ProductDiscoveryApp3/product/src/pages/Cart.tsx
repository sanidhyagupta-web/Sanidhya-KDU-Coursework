import React from "react";
import { useNavigate } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../hooks";
import {
  selectCartItemsArray,
  selectTotalItems,
  selectTotalPrice,
  increment,
  decrement,
  removeFromCart,
} from "../thunks/cartSlice";
import "../styles/main.scss";

const Cart = () => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();

  const items = useAppSelector(selectCartItemsArray);
  const totalItems = useAppSelector(selectTotalItems);
  const totalPrice = useAppSelector(selectTotalPrice);

  if (items.length === 0) {
    return (
      <div className="cart-page">
        <h2>Shopping Cart</h2>
        <p>Your cart is empty.</p>
        <button className="primary" onClick={() => navigate("/")}>
          Continue Shopping
        </button>
      </div>
    );
  }

  return (
    <div className="cart-page">
      <h2>Shopping Cart</h2>

      <div className="cart-layout">

        <div className="cart-items">
          {items.map(({ product, quantity }) => {
            const id = String(product.id);
            const price = Number(product.price ?? 0);
            const itemTotal = price * quantity;

            return (
              <div className="cart-item" key={id}>
                <img
                  className="cart-img"
                  src={(product as any).thumbnail}
                  alt={(product as any).title}
                />

                <div className="cart-info">
                  <div className="cart-title">{(product as any).title}</div>

                  <div className="qty">
                    <span>Quantity:</span>
                    <button onClick={() => dispatch(decrement(id))}>-</button>
                    <span className="qty-num">{quantity}</span>
                    <button onClick={() => dispatch(increment(id))}>+</button>
                  </div>
                </div>

                <div className="cart-right">
                  <div className="item-total">${itemTotal.toFixed(2)}</div>
                  <button
                    className="remove"
                    onClick={() => dispatch(removeFromCart(id))}
                  >
                    Remove
                  </button>
                </div>
              </div>
            );
          })}
        </div>

        {/* Right: summary */}
        <div className="cart-summary">
          <h3>Order Summary</h3>

          <div className="row">
            <span>Total Items:</span>
            <span>{totalItems}</span>
          </div>

          <div className="row total">
            <span>Total Price:</span>
            <span>${totalPrice.toFixed(2)}</span>
          </div>

          <button className="primary" onClick={() => navigate("/")}>
            Continue Shopping
          </button>
        </div>
      </div>
    </div>
  );
};

export default Cart;
