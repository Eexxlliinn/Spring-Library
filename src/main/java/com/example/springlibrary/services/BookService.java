package com.example.springlibrary.services;

import com.example.springlibrary.entities.Book;
import com.example.springlibrary.forms.BookForm;
import com.example.springlibrary.repositories.BookReporisoty;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private BookReporisoty bookReporisoty;

    @Autowired
    public BookService(BookReporisoty bookReporisoty) {
        this.bookReporisoty = bookReporisoty;
    }

    public List<Book> getAllBooks() {
        return bookReporisoty.findAll();
    }

    public Book getBookById(Long id) {
        return bookReporisoty.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
    }

    public Book getBookByIsbn(String Isbn) {
        return bookReporisoty.findByIsbn(Isbn)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with Isbn: " + Isbn));
    }

    @Transactional
    public Book createBook(Book book) {
        if(!bookReporisoty.existsByIsbn(book.getIsbn())) {
            return bookReporisoty.save(book);
        } else {
            throw new RuntimeException("Book with this Isbn is already existed.");
        }
    }

    @Transactional
    public Book updateBook(Long id, Book updatedBook) {
        Book existingBook = getBookById(id);
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setGenre(updatedBook.getGenre());
        existingBook.setDescription(updatedBook.getDescription());
        existingBook.setAuthor(updatedBook.getAuthor());
        bookReporisoty.save(existingBook);
        return existingBook;
    }

    @Transactional
    public void deleteBook(Long id) {
        bookReporisoty.deleteById(id);
    }

}
