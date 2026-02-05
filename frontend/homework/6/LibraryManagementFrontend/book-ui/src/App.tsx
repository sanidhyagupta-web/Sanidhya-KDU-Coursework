import { useEffect, useMemo, useState } from "react";
import "./styles/main.css";
import { useDebounce } from "./hooks/useDebounce";
import { useBooks } from "./hooks/useBooks";
import type { Book } from "./types/book";
import { Filters } from "./Component/Filters";
import { BookList } from "./Component/BookList";
import { BookStats } from "./Component/BookStats";
import { booksApi } from "./api/books.api";

export default function App() {
  const [query, setQuery] = useState("");
  const debouncedQuery = useDebounce(query, 350);

  const [genre, setGenre] = useState<string>("All");
  const [minRating, setMinRating] = useState<number>(0);

  // data state (fetched from API)
  const { books, loading, error,setBooks } = useBooks(debouncedQuery);
  useEffect(() => {
    if (genre == "All"){
      booksApi.getAll().then(setBooks)
    }else{
      booksApi.getByGenre(genre).then(setBooks)
    }
  },[genre, setBooks])


  // derive genres from data
  const genres = useMemo(() => {
    const set = new Set<string>();
    books.forEach((b) => set.add(b.genre));
    return ["All", ...Array.from(set).sort()];
  }, [books]);

  const visibleBooks = useMemo(() => {
  const q = query.trim().toLowerCase();

  return books.filter((b) => {
    const queryOk =
      !q ||
      b.title.toLowerCase().includes(q) ||
      b.author.toLowerCase().includes(q);

    const genreOk = genre === "All" ? true : b.genre === genre;
    const ratingOk = b.rating >= minRating;

    return queryOk && genreOk && ratingOk;
  });
}, [books, query, genre, minRating]);


  return (
    <div className="page">
      <header className="header">
        <h1>Book Library</h1>
        <p className="muted">Search and filter your catalog.</p>
      </header>

      <Filters
        query={query}
        setQuery={setQuery}
        genres={genres}
        genre={genre}
        setGenre={setGenre}
        minRating={minRating}
        setMinRating={setMinRating}
      />

      <BookStats books={books} />

      <section className="listSection">
        <h2 className="sectionTitle">Books</h2>

        {error && <div className="error">{error}</div>}
        {loading && <div className="muted">Loading…</div>}

        {!loading && visibleBooks.length === 0 ? (
          <div className="muted">No books match your filters.</div>
        ) : (
          <BookList books={visibleBooks} />
        )}
      </section>
    </div>
  );
}
