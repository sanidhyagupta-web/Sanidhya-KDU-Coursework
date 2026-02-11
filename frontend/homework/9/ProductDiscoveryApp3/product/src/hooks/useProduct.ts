import { useEffect } from "react"; 
import { useAppDispatch, useAppSelector } from "../hooks";
 import { fetchAllProducts } from "../thunks/product";
 
 export function useProducts() {
     const dispatch = useAppDispatch(); 
     const state = useAppSelector((s) => s.products); 
     useEffect(() => { dispatch(fetchAllProducts()); }, [dispatch]); 
     return { data: state.productList, loading: state.Loadingstate, error: state.errorstate, };
}