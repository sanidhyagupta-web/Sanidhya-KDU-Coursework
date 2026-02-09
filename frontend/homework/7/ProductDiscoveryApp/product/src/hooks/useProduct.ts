import type { product,productResponse } from "../types/product"
import { useApi } from "./useApi"

const BASE_URL = "https://dummyjson.com/products"

export function useProducts (){
    return useApi<productResponse>(BASE_URL)
}

export function useProductsById(id? : number){
    return useApi<product>(
        id? BASE_URL+`/${id}` : null
    );
}