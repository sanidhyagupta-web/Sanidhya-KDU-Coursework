import { useEffect, useRef, useState } from "react";

type ApiState<T> = {
  data: T | null;
  loading: boolean;
  error: string | null;
};

export function useApi<T>(url: string | null) {
  const [state, setState] = useState<ApiState<T>>({
    data: null,
    loading: false,
    error: null,
});

  const abortRef = useRef<AbortController | null>(null);

  const fetchData = async () => {
    if (!url) return;

    abortRef.current?.abort();
    const controller = new AbortController();
    abortRef.current = controller;

    setState({ data: null, loading: true, error: null });

    try {
      const res = await fetch(url, { signal: controller.signal });

      if (!res.ok) {
        throw new Error(`Error ${res.status}: ${res.statusText}`);
      }

      const json = (await res.json()) as T;
      setState({ data: json, loading: false, error: null });
    } catch (err: any) {
      if (err.name === "AbortError") return;
      setState({
        data: null,
        loading: false,
        error: err.message || "Something went wrong",
      });
    }
  };

  useEffect(() => {
    fetchData();
    return () => abortRef.current?.abort();
  }, [url]);

  return {
    ...state,
    refetch: fetchData,
  };
}
