package com.LibraryManagement2.ProductionReadyLib.Controller;

import com.LibraryManagement2.ProductionReadyLib.Entities.Loan;
import com.LibraryManagement2.ProductionReadyLib.Entities.Status;
import com.LibraryManagement2.ProductionReadyLib.LibraryServiceImpl.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/loans/v1")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/{bookId}/borrow/{userId}")
    public ResponseEntity<Loan> addBorrowLoan(@PathVariable("userId") UUID userId, @PathVariable("bookId") UUID bookId){
        return new ResponseEntity<>(loanService.addLoanBorrower(userId,bookId), HttpStatus.CREATED);
    }

    @PostMapping("/{bookId}/return/{userId}")
    public ResponseEntity<Loan> returnLoan(@PathVariable("userId") UUID userId, @PathVariable("bookId") UUID bookId){
        return new ResponseEntity<>(loanService.addLoanReturn(userId,bookId),HttpStatus.CREATED);
    }

    @GetMapping("/analytics/audit")
    public ResponseEntity<Map<Status,Long>> getAnalytics(){
        return new ResponseEntity<>(loanService.getAnalytics(),HttpStatus.OK);
    }
}
