import { BadRequestException, Controller, Get, Param, ParseIntPipe, Query } from '@nestjs/common';
import { BookService } from './book.service';

@Controller('book')
export class BookController {
  constructor(private readonly bookService: BookService) {}

  @Get()
  async getAllBooks() {
    return this.bookService.fetchBooksFromData();
  }

  @Get('search')
  async searchBook(@Query('query') query: string) {
    if (!query?.trim()) {
      throw new BadRequestException('query is required');
    }
    return this.bookService.searchBooks(query);
  }

  @Get('genre')
  async genreBook(@Query('genre') genre: string){
    if(!genre.trim()){
      throw new BadRequestException('genre is required');
    }
    return this.bookService.getgenreBooks(genre)
  }
  
  @Get('available')
  async availableBook() {
    return this.bookService.getAvailableBooks();
  }

  @Get('year-range/:startYear/:endYear')
  async getBooksByYearRange(
    @Param('startYear', ParseIntPipe) startYear: number,
    @Param('endYear', ParseIntPipe) endYear: number,
  ){
        return this.bookService.getBooksByYearRange(startYear,endYear)
  }
}
