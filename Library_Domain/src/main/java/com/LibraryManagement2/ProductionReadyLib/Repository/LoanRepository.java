package com.LibraryManagement2.ProductionReadyLib.Repository;

import com.LibraryManagement2.ProductionReadyLib.Entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
}