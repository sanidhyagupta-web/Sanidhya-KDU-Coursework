export type Genre =
  | "Fiction"
  | "Non-Fiction"
  | "Fantasy"
  | "Science"
  | "History"
  | "Biography"
  | "Mystery";

export type Book = {
  id: number;
  title: string;
  author: string;
  genre: Genre;
  year: number;
  pages: number;
  rating: number;
  available: boolean;
  description?: string;
};
