import type { Book } from "../types/book";
import { BookCard } from "./BookCard";

export function BookList({ books }: { books: Book[] }) {
  return (
    <div className="grid">
      {books.map((b) => (
        <BookCard key={b.id} book={b} />
      ))}
    </div>
  );
}
