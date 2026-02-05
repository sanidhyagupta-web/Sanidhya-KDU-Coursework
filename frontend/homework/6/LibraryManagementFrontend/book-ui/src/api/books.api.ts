import { fetchJson } from "./http";
import type { Book } from "../types/book";

const API_BASE = "http://localhost:3000/book"; 

export const booksApi = {
  getAll: () => fetchJson<Book[]>(API_BASE),
  search: (query: string) =>
    fetchJson<Book[]>(`${API_BASE}/search?query=${encodeURIComponent(query)}`),
  getByGenre: (genre: string) =>
    fetchJson<Book[]>(`${API_BASE}/genre?genre=${genre}`),

};