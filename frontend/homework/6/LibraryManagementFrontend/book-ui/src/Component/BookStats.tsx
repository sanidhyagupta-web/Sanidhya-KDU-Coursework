import { useMemo } from "react";
import type { Book } from "../types/book";

export function BookStats({ books }: { books: Book[] }) {
  const stats = useMemo(() => {
    const total = books.length;
    const available = books.filter((b) => b.available).length;
    const unavailable = total - available;
    const avgRating = total === 0 ? 0 : books.reduce((s, b) => s + b.rating, 0) / total;

    const byGenreMap = new Map<string, number>();
    books.forEach((b) => byGenreMap.set(b.genre, (byGenreMap.get(b.genre) || 0) + 1));
    const byGenre = Array.from(byGenreMap.entries()).sort((a, b) => b[1] - a[1]);

    return { total, available, unavailable, avgRating, byGenre };
  }, [books]);

  return (
    <section className="stats">
      <h2 className="sectionTitle">Statistics</h2>

      <div className="stats__grid">
        <div className="statBox">
          <div className="statNum">{stats.total}</div>
          <div className="statLabel">Total books</div>
        </div>
        <div className="statBox">
          <div className="statNum">{stats.available}</div>
          <div className="statLabel">Available</div>
        </div>
        <div className="statBox">
          <div className="statNum">{stats.unavailable}</div>
          <div className="statLabel">Unavailable</div>
        </div>
        <div className="statBox">
          <div className="statNum">{stats.avgRating.toFixed(2)}</div>
          <div className="statLabel">Average rating</div>
        </div>
      </div>

      <div className="genreCounts">
        <h3 className="subTitle">Books by genre</h3>
        {stats.byGenre.length === 0 ? (
          <div className="muted">No data</div>
        ) : (
          <ul className="genreList">
            {stats.byGenre.map(([g, c]) => (
              <li key={g}>
                <span>{g}</span>
                <span className="pill">{c}</span>
              </li>
            ))}
          </ul>
        )}
      </div>
    </section>
  );
}
