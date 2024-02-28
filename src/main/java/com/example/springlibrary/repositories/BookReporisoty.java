package com.example.springlibrary.repositories;

import com.example.springlibrary.entities.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookReporisoty extends CrudRepository<Book, Long> {

    @Override
    List<Book> findAll();
    Optional<Book> findByIsbn(String Isbn);
    boolean existsByIsbn(String Isbn);


}
