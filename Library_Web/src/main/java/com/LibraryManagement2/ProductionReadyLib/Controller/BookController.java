package com.LibraryManagement2.ProductionReadyLib.Controller;

import com.LibraryManagement2.ProductionReadyLib.Entities.Book;
import com.LibraryManagement2.ProductionReadyLib.Entities.Status;
import com.LibraryManagement2.ProductionReadyLib.LibraryServiceImpl.BookService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books/v1")
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(
            summary = "Create a new book",
            description = "Creates a book in PROCESSING state. Only LIBRARIAN can access this API."
    )
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Move book from PROCESSING to AVAILABLE",
            description = "Explicit catalog transition. Handles optimistic locking."
    )
    @PatchMapping("/{id}/catalog/{status}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") UUID id){
        return new ResponseEntity<>(bookService.updateBook(id),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Book>> getBooks(
            @RequestParam(name = "status" , required = false ) Status status ,
            @RequestParam(name = "title", required = false) String titleContains,
            @PageableDefault(size = 5)
            Pageable pageable
            ){
        return new ResponseEntity<>(bookService.getAllBooks(status,titleContains,pageable),HttpStatus.OK);
    }

}