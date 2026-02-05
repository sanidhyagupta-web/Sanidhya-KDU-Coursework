type FiltersProps = {
  query: string;
  setQuery: (v: string) => void;
  genres: string[];
  genre: string;
  setGenre: (v: string) => void;
  minRating: number;
  setMinRating: (v: number) => void;
};

export function Filters({
  query,
  setQuery,
  genres,
  genre,
  setGenre,
  minRating,
  setMinRating,
}: FiltersProps) {
  return (
    <section className="controls">
      <div className="control">
        <label className="label">Search (title or author)</label>
        <input
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          placeholder="e.g. Tolkien / Clean Code"
        />
      </div>

      <div className="control">
        <label className="label">Genre</label>
        <select value={genre} onChange={(e) => setGenre(e.target.value)}>
          {genres.map((g) => (
            <option key={g} value={g}>
              {g}
            </option>
          ))}
        </select>
      </div>

      <div className="control">
        <label className="label">Minimum rating: {minRating.toFixed(1)}</label>
        <input
          type="range"
          min={0}
          max={5}
          step={0.5}
          value={minRating}
          onChange={(e) => setMinRating(Number(e.target.value))}
        />
      </div>
    </section>
  );
}
