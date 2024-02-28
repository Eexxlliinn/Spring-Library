package com.example.springlibrary.repositories;

import com.example.springlibrary.entities.Book;
import com.example.springlibrary.entities.BorrowedBook;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowedBooksRepository extends CrudRepository<BorrowedBook, Long> {

    @Override
    List<BorrowedBook> findAll();
    BorrowedBook findByBookAndBorrowedDateIsNotNullAndReturnDateIsNull(Book book);
    List<BorrowedBook> findAllByBorrowedDateIsNotNullAndReturnDateIsNull();
    BorrowedBook findTopByBookOrderByIdDesc(Book book);
    void deleteAllByBook(Book book);

}
