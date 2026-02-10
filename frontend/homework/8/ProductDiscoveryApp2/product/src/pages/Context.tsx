import React, {
  createContext,
  useState,
  useCallback,
  useContext,
  type ReactNode,
} from "react";
import type { product, productResponse } from "../types/product";
import { useProducts } from "../hooks/useProduct";

type ProductContextType = {
  products: product[];
  searchQuery: string;
  selectedProduct: product | null;
  loading: boolean;
  searchLoading: boolean;
  error: string | null;

  fetchAllProducts: () => Promise<void>;
  searchProducts: (query?: string) => Promise<void>;
  fetchProductById: (id: string) => Promise<void>;
  setSearchQuery: (q: string) => void;
  clearSearch: () => Promise<void>;
};

const ProductContext = createContext<ProductContextType | null>(null);

type ProductProviderProps = {
  children: ReactNode;
};

export function ProductProvider({ children }: ProductProviderProps) {
  const [products, setProducts] = useState<product[]>([]);
  const [searchQuery, setSearchQuery] = useState<string>("");
  const [selectedProduct, setSelectedProduct] = useState<product | null>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const [searchLoading, setSearchLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const BASE_URL = "https://dummyjson.com/products";

  const clearError = useCallback(() => setError(null), []);

  const fetchAllProducts = useCallback(async (): Promise<void> => {
    clearError();
    setLoading(true);

    try {
      const res = await fetch(BASE_URL);
      if (!res.ok) throw new Error(`Error ${res.status}: ${res.statusText}`);
      const data: productResponse = await res.json();
      setProducts(data.products);
    } catch (err: any) {
      setError(err?.message ?? "Failed to fetch products");
    } finally {
      setLoading(false);
    }
  }, [clearError]);

  const searchProducts = useCallback(
    async (query?: string): Promise<void> => {
      const q = (query ?? searchQuery)?.trim();
      if (!q) {
        await fetchAllProducts();
        return;
      }

      clearError();
      setSearchLoading(true);

      try {
        const url = BASE_URL + `/search?q=${encodeURIComponent(q)}`;
        const res = await fetch(url);
        if (!res.ok) throw new Error(`Error ${res.status}: ${res.statusText}`);
        const data: productResponse = await res.json();
        setProducts(data.products);
      } catch (err: any) {
        setError(err?.message ?? "Failed to search products");
      } finally {
        setSearchLoading(false);
      }
    },
    [searchQuery, fetchAllProducts, clearError]
  );

  const fetchProductById = useCallback(async (id: string): Promise<void> => {
    if (!id) return;
    clearError();
    setLoading(true);

    try {
      const url = BASE_URL + `/${id}`;
      console.log(url);
      const res = await fetch(url);
      if (!res.ok) throw new Error(`Error ${res.status}: ${res.statusText}`);
      const data: product = await res.json();
      setSelectedProduct(data);
    } catch (err: any) {
      setError(err?.message ?? "Failed to fetch product details");
    } finally {
      setLoading(false);
    }
  }, [clearError]);

  const clearSearch = useCallback(async (): Promise<void> => {
    setSearchQuery("");
    // reload all products after clearing search (optional)
    await fetchAllProducts();
  }, [fetchAllProducts]);

  // provider value
  const value: ProductContextType = {
    products,
    searchQuery,
    selectedProduct,
    loading,
    searchLoading,
    error,

    fetchAllProducts,
    searchProducts,
    fetchProductById,
    setSearchQuery,
    clearSearch,
  };

  return (
    <ProductContext.Provider value={value}>{children}</ProductContext.Provider>
  );
}

export function useProductContext(): ProductContextType {
  const ctx = useContext(ProductContext);
  if (!ctx) {
    throw new Error("useProductContext must be used inside a ProductProvider");
  }
  return ctx;
}

export default ProductProvider;
