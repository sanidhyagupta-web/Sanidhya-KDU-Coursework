import { createSlice, configureStore ,createAsyncThunk} from '@reduxjs/toolkit';
import type { product, productResponse } from "../types/product";

const BASE_URL = "https://dummyjson.com/products";

interface ProductState {
    productList : product[],
    searchQuery : string,
    Selectedproduct :null | product,
    Loadingstate : boolean,
    Searchloadingstate : boolean,
    errorstate : string | null;
}

const initialState: ProductState = {
  productList: [],
  searchQuery: "",
  Selectedproduct: null,
  Loadingstate: false,
  Searchloadingstate: false,
  errorstate: null,
};

export const fetchAllProducts =  createAsyncThunk(
    "Products/FetchProduct",
    async() : Promise<product[]> => {
        const response = await fetch(BASE_URL)
        if(!response.ok){
            throw new Error(`Error in fetchAllProducts`)
        }
        const data = await response.json()
        return data.products;
    }
)

export const fetchProductsById = createAsyncThunk(
    "Products/FetchProductById",
    async(id : string) : Promise<product> => {
        const response = await fetch(`${BASE_URL}/${id}`)
        if(!response.ok){
            throw new Error(`Error in fetchProductsById`)
        }
        const data = await response.json()
        return data
    }
)

export const searchProduct = createAsyncThunk(
    "Products/SearchProduct",
    async(query : string) : Promise<product[]> => {
        const response = await fetch(`${BASE_URL}/search?q=${encodeURIComponent(query)}`)
        if(!response.ok){
            throw new Error(`Error in searching the product`)
        }
        const data = await response.json()
        return data.products
    }
)

const ProductSlice = createSlice({
    name: "Products",
    initialState,
    reducers: {
        setSearchQuery(state , action : {payload : string}){
            state.searchQuery = action.payload
        },
        clearSearch(state){
            state.searchQuery = ""
        }
    },
  extraReducers: (builder) => {
    builder
      // fetchAllProducts
      .addCase(fetchAllProducts.pending, (state) => {
        state.Loadingstate = true;
        state.errorstate = null;
      })
      .addCase(fetchAllProducts.fulfilled, (state, action) => {
        state.Loadingstate = false;
        state.productList = action.payload;
      })
      .addCase(fetchAllProducts.rejected, (state, action) => {
        state.Loadingstate = false;
        state.errorstate = action.error.message ?? "Fetch all failed";
      })

      // fetchProductsById
      .addCase(fetchProductsById.pending, (state) => {
        state.Loadingstate = true;
        state.errorstate = null;
      })
      .addCase(fetchProductsById.fulfilled, (state, action) => {
        state.Loadingstate = false;
        state.Selectedproduct = action.payload;
      })
      .addCase(fetchProductsById.rejected, (state, action) => {
        state.Loadingstate = false;
        state.errorstate = action.error.message ?? "Fetch by id failed";
      })

      // searchProduct
      .addCase(searchProduct.pending, (state) => {
        state.Searchloadingstate = true;
        state.errorstate = null;
      })
      .addCase(searchProduct.fulfilled, (state, action) => {
        state.Searchloadingstate = false;
        state.productList = action.payload;
      })
      .addCase(searchProduct.rejected, (state, action) => {
        state.Searchloadingstate = false;
        state.errorstate = action.error.message ?? "Search failed";
      });
  },
})

export const {setSearchQuery , clearSearch} = ProductSlice.actions
export default ProductSlice.reducer;