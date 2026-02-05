import type { Book } from "../types/book";
import { AvailabilityBadge } from "./AvailabilityBadge";

export function BookCard({ book }: { book: Book }) {
  return (
    <div className={`card ${book.available ? "card--available" : "card--unavailable"}`}>
      <div className="card__top">
        <h3 className="card__title">{book.title}</h3>
        <AvailabilityBadge available={book.available} />
      </div>

      <div className="card__meta">
        <div><span className="label">Author:</span> {book.author}</div>
        <div><span className="label">Genre:</span> {book.genre}</div>
        <div><span className="label">Rating:</span> {book.rating.toFixed(1)} / 5</div>
        <div><span className="label">Title:</span> {book.title}</div>
        
      </div>
    </div>
  );
}
