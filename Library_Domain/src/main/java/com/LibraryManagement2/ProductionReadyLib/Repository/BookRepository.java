package com.LibraryManagement2.ProductionReadyLib.Repository;

import com.LibraryManagement2.ProductionReadyLib.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> , JpaSpecificationExecutor<Book> {

}
