package com.LibraryManagement2.ProductionReadyLib.LibraryServiceImpl;

import com.LibraryManagement2.ProductionReadyLib.Entities.Book;
import com.LibraryManagement2.ProductionReadyLib.Entities.Loan;
import com.LibraryManagement2.ProductionReadyLib.Entities.Status;
import com.LibraryManagement2.ProductionReadyLib.Entities.User;
import com.LibraryManagement2.ProductionReadyLib.Exceptions.BookNotAvailableException;
import com.LibraryManagement2.ProductionReadyLib.Exceptions.ResourceNotFoundException;
import com.LibraryManagement2.ProductionReadyLib.Repository.BookRepository;
import com.LibraryManagement2.ProductionReadyLib.Repository.LoanRepository;
import com.LibraryManagement2.ProductionReadyLib.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Loan addLoanBorrower(UUID userId, UUID bookId){
        Book book = bookRepository.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book with id "+bookId+ " was not found"));
        if(book.getStatus() == Status.AVAILABLE){
            book.setStatus(Status.CHECKED_OUT);
        }else{
            throw new BookNotAvailableException("Book Not Available");
        }
        bookRepository.save(book);
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with id " +userId + " was not found"));
        Loan loan = Loan.builder().book(book).borrower(user).build();
        return loanRepository.save(loan);
    }

    @Transactional
    public Loan addLoanReturn(UUID userId,UUID bookId){
        Book book = bookRepository.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book with id "+bookId+ " was not found"));
        if(book.getStatus() == Status.CHECKED_OUT){
            book.setStatus(Status.AVAILABLE);

        }else{
            throw new BookNotAvailableException("Book Not Available");
        }
        bookRepository.save(book);
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with id " +userId + " was not found"));
        Loan loan = Loan.builder().book(book).borrower(user).returnedAt(Instant.now()).build();
        return loanRepository.save(loan);
    }

    public Map<Status, Long> getAnalytics() {
        List<Book> books = bookRepository.findAll();
        return books.stream().collect(Collectors.groupingBy(Book::getStatus,Collectors.counting()));
    }
}
