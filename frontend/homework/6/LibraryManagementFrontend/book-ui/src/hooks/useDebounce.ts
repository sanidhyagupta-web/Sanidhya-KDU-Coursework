import { useEffect, useState } from "react";

// We use this because we don't want the api to get hit automatically when something changes in the text box .
// There should be some delay associated with the text box when the user types something. 
export function useDebounce<T>(value: T, delayMs: number): T {
  const [debounced, setDebounced] = useState(value);

  useEffect(() => {
    const t = setTimeout(() => setDebounced(value), delayMs);

    return () => clearTimeout(t);
  }, [value, delayMs]);

  return debounced;
}