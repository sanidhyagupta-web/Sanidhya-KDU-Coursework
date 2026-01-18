package com.LibraryManagement2.ProductionReadyLib.LibraryServiceImpl;

import com.LibraryManagement2.ProductionReadyLib.Entities.Book;
import com.LibraryManagement2.ProductionReadyLib.Entities.Status;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecifications {

    public static Specification<Book> hasStatus(Status status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Book> titleContains(String title) {
        return (root, query, cb) ->
                (title == null || title.isBlank())
                        ? null
                        : cb.like(
                        cb.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                );
    }

}
