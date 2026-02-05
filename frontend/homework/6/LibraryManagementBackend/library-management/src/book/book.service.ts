import { BadRequestException, Injectable } from '@nestjs/common';
import { BOOKS } from './book.data';
import { Book } from './book.model';

@Injectable()
export class BookService {

    private books : Book[] = BOOKS

    async fetchBooksFromData() : Promise<Book[]>{
        return this.books
    }

    async searchBooks(query : string) : Promise<Book[]>{
        query = query.trim().toLowerCase()

        return this.books.filter(
            (book) => 
                book.title.toLowerCase().includes(query) || 
                book.author.toLowerCase().includes(query),
        );

    }

    async getAvailableBooks() : Promise<Book[]>{
        return this.books.filter((book) => book.available === true)
    }

    async getgenreBooks(genre : string) : Promise<Book[]>{
        return this.books.filter((book) => book.genre === genre);
    }

    async getBooksByYearRange(startYear: number,endYear: number,): Promise<Book[]> {
            if (isNaN(startYear) || isNaN(endYear)) {
                    throw new BadRequestException('startYear and endYear must be numbers');
            }

            if (startYear > endYear) {
                    throw new BadRequestException(
                        'startYear must be less than or equal to endYear',);
            }

            if (startYear < 1000 || endYear < 1000) {
                    throw new BadRequestException('Year must be greater than or equal to 1000');
            }

            return this.books.filter(
                    (book) => book.year >= startYear && book.year <= endYear,
            );
    }}
