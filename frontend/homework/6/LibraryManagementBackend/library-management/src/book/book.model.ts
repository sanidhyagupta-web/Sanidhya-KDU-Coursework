import { Genre } from "./genre.enum";

export interface Book {
  id: number;
  title: string;
  author: string;
  genre: Genre;
  year: number;
  pages: number;
  rating: number;
  available: boolean;
  description?: string;
}
