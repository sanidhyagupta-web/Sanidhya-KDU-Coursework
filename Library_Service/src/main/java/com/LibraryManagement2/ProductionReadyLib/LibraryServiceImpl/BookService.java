package com.LibraryManagement2.ProductionReadyLib.LibraryServiceImpl;

import com.LibraryManagement2.ProductionReadyLib.Entities.Book;
import com.LibraryManagement2.ProductionReadyLib.Entities.Status;
import com.LibraryManagement2.ProductionReadyLib.Exceptions.ResourceNotFoundException;
import com.LibraryManagement2.ProductionReadyLib.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service responsible for managing books in the library.
 * <p>
 * Handles book creation, catalog updates, and availability state transitions.
 */
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    /**
     * Creates a new book in PROCESSING state.
     *
     * @param book creation data
     * @return created book details
     */
    public Book addBook(Book book){
        book.setStatus(Status.PROCESSING);
        return bookRepository.save(book);
    }

    /**
     * Updates a book which is in PROCESSING state and makes it AVAILABLE
     *
     * @param id getting book
     * @return created Book details
     */
    public Book updateBook(UUID id){
        Book book = bookRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Resource with id " + id +" not found"));
        if(book.getStatus() != Status.PROCESSING){
            throw new IllegalStateException("Illegal State");
        }
        book.setStatus(Status.AVAILABLE);
        return bookRepository.save(book);
    }

    public Book getBook(UUID id){
       return bookRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Resource with id " + id +" not found"));
    }

    /**
     * Get the details of all book with Optional status and title with Pagination and Sorting.
     *
     * @param status -> Optional
     * @param titleContains -> Optional
     * @param pageable -> contains pagesize, pageno and sort
     * @return Page of Books
     */
    public Page<Book> getAllBooks(Status status, String titleContains, Pageable pageable) {
        Specification<Book> spec = Specification
                .where(BookSpecifications.hasStatus(status))
                .and(BookSpecifications.titleContains(titleContains));

        return bookRepository.findAll(spec,pageable);
    }
}
