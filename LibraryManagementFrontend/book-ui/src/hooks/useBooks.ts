import { useEffect, useState } from "react";
import type { Book } from "../types/book";
import { booksApi } from "../api/books.api";

type UseBooksResult = {
  books: Book[];
  loading: boolean;
  error: string;
  setBooks: React.Dispatch<React.SetStateAction<Book[]>>;
};

export function useBooks(searchQuery: string): UseBooksResult {
  const [books, setBooks] = useState<Book[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  // Get all the books
  useEffect(() => {
    const q = searchQuery.trim();
    if (!q) return;

    (async () => {
      try {
        setLoading(true);
        setError("");
        const data = await booksApi.getAll();
        setBooks(data);
      } catch (e: any) {
        setError(e?.message ?? "Failed to load books");
      } finally {
        setLoading(false);
      }
    })();
  }, [searchQuery]);

  // Search books by title or author
  useEffect(() => {
    const q = searchQuery.trim();
    if (!q) return;

    (async () => {
      try {
        setLoading(true);
        setError("");
        const data = await booksApi.search(q);
        setBooks(data);
      } catch (e: any) {
        setError(e?.message ?? "Search failed");
      } finally {
        setLoading(false);
      }
    })();
  }, [searchQuery]);

  return { books, loading, error, setBooks };
}
