import { createSlice } from "@reduxjs/toolkit";
import type { PayloadAction } from "@reduxjs/toolkit";
import type { product } from "../types/product";

type CartItem = {
  product: product;
  quantity: number;
};

type CartState = {
  items: Record<string, CartItem>; 
};

const initialState: CartState = {
  items: {},
};

const cartSlice = createSlice({
  name: "cart",
  initialState,
  reducers: {
    addToCart(state, action: PayloadAction<product>) {
      const p = action.payload;
      const id = String(p.id);

      if (state.items[id]) {
        state.items[id].quantity += 1;
      } else {
        state.items[id] = { product: p, quantity: 1 };
      }
    },
    increment(state, action: PayloadAction<string>) {
      const id = action.payload;
      if (state.items[id]) state.items[id].quantity += 1;
    },
    decrement(state, action: PayloadAction<string>) {
      const id = action.payload;
      if (!state.items[id]) return;

      state.items[id].quantity -= 1;
      if (state.items[id].quantity <= 0) {
        delete state.items[id];
      }
    },
    removeFromCart(state, action: PayloadAction<string>) {
      delete state.items[action.payload];
    },
    clearCart(state) {
      state.items = {};
    },
  },
});

export const { addToCart, increment, decrement, removeFromCart, clearCart } =
  cartSlice.actions;

export default cartSlice.reducer;

export const selectCartItemsArray = (state: any) => Object.values(state.cart.items) as CartItem[];

export const selectTotalItems = (state: any) =>
  Object.values(state.cart.items).reduce((sum: number, item: any) => sum + item.quantity, 0);

export const selectTotalPrice = (state: any) =>
  Object.values(state.cart.items).reduce((sum: number, item: any) => {
    const price = Number(item.product.price ?? 0);
    return sum + price * item.quantity;
  }, 0);
