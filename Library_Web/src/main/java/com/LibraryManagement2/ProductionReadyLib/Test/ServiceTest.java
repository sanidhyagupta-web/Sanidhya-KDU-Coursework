package com.LibraryManagement2.ProductionReadyLib.Test;

import com.LibraryManagement2.ProductionReadyLib.Entities.Book;
import com.LibraryManagement2.ProductionReadyLib.Entities.Status;
import com.LibraryManagement2.ProductionReadyLib.Exceptions.ResourceNotFoundException;
import com.LibraryManagement2.ProductionReadyLib.LibraryServiceImpl.BookService;
import com.LibraryManagement2.ProductionReadyLib.Repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ServiceTest {

    @Autowired private BookService bookService;
    @Autowired private BookRepository bookRepository;

    @Test
    public void catalog_book_from_processing_to_available() {
        Book book = new Book();
        book.setTitle("Spring");
        book.setStatus(Status.PROCESSING);

        book = bookRepository.save(book);

        bookService.updateBook(book.getId());

        Book updated = bookRepository.findById(book.getId()).orElseThrow();
        assertThat(updated.getStatus()).isEqualTo(Status.AVAILABLE);
    }

    @Test
    public void catalog_book_from_processing_to_available_with_exception() {
        Book book = new Book();
        book.setTitle("Spring");
        book.setStatus(Status.PROCESSING);

        book = bookRepository.save(book);
        UUID rand = UUID.randomUUID();
        assertThatThrownBy(() -> bookService.updateBook(rand))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Resource with id " + rand + " not found");
    }

//    @Test
//    void existsByBookIdAndStatus_returns_true_when_active_loan_exists() {
//        Book book = bookRepository.save(new Book("Spring", Status.AVAILABLE));
//
//        loanRepository.saveAndFlush(new Loan(null, book, LoanStatus.ACTIVE));
//
//        assertThat(loanRepository.existsByBookIdAndStatus(book.getId(), LoanStatus.ACTIVE))
//                .isTrue();
//    }

}

