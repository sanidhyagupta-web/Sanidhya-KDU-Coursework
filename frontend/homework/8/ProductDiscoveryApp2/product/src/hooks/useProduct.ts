import type { product,productResponse } from "../types/product"
import { useApi } from "./useApi"

const BASE_URL = "https://dummyjson.com/products"

export function useProducts (){
    return useApi<productResponse>(BASE_URL)
}

export function useProductsById(id? : string){
    return useApi<product>(
        id? BASE_URL+`/${id}` : null
    );
}

export function useProductsBySearch(query? : string){
    const url = query && String(query).trim() !== ""
        ? BASE_URL + `/search?q=${encodeURIComponent(query)}`
        : BASE_URL + `/search?q=`;

    return useApi<productResponse>(url);
}