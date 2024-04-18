package com.example.springlibrary.services;

import com.example.springlibrary.entities.Book;
import com.example.springlibrary.entities.BorrowedBook;
import com.example.springlibrary.repositories.BookReporisoty;
import com.example.springlibrary.repositories.BorrowedBooksRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class LibraryService {
    private BookReporisoty bookReporisoty;
    private BorrowedBooksRepository borrowedBooksRepository;

    @Autowired
    public LibraryService(BookReporisoty bookReporisoty, BorrowedBooksRepository borrowedBooksRepository) {
        this.bookReporisoty = bookReporisoty;
        this.borrowedBooksRepository = borrowedBooksRepository;
    }

    public List<BorrowedBook> getBorrowedBooks() {
        return borrowedBooksRepository.findAll();
    }
    public List<BorrowedBook> getAvailableBooks() {
        List<Book> allBooks = bookReporisoty.findAll();
        List<BorrowedBook> availableBooks = new ArrayList<>();
        for (Book book : allBooks) {
            BorrowedBook borrowedBook = borrowedBooksRepository.findTopByBookOrderByIdDesc(book);
            if ((borrowedBook.getBorrowedDate() == null && borrowedBook.getReturnDate() == null) || (borrowedBook.getBorrowedDate() != null && borrowedBook.getReturnDate() != null)) {
                availableBooks.add(borrowedBook);
            }
        }
        return availableBooks;
    }
    public List<BorrowedBook> getNotAvailableBooks() {
        return borrowedBooksRepository.findAllByBorrowedDateIsNotNullAndReturnDateIsNull();
    }
    @Transactional
    public void borrowBook(Long id) {
        Optional<Book> optionalBook = bookReporisoty.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            BorrowedBook lastBorrowedBook = borrowedBooksRepository.findTopByBookOrderByIdDesc(book);
            if (lastBorrowedBook != null && lastBorrowedBook.getBorrowedDate() == null && lastBorrowedBook.getReturnDate() == null) {
                lastBorrowedBook.setBorrowedDate(LocalDate.now());
                lastBorrowedBook.setBorrowedTime(LocalTime.now());
                borrowedBooksRepository.save(lastBorrowedBook);
            } else {
                BorrowedBook newBorrowedBook = new BorrowedBook();
                newBorrowedBook.setBook(book);
                newBorrowedBook.setBorrowedDate(LocalDate.now());
                newBorrowedBook.setBorrowedTime(LocalTime.now());
                borrowedBooksRepository.save(newBorrowedBook);
            }
        } else {
            throw new EntityNotFoundException("Book not found with id: " + id);
        }

    }
    @Transactional
    public void returnBook(Long borrowedBookId) {
        Optional<Book> optionalBook = bookReporisoty.findById(borrowedBookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            BorrowedBook borrowedBook = borrowedBooksRepository.findByBookAndBorrowedDateIsNotNullAndReturnDateIsNull(book);
            borrowedBook.setReturnTime(LocalTime.now());
            borrowedBook.setReturnDate(LocalDate.now());
            borrowedBooksRepository.save(borrowedBook);
        } else {
            throw new EntityNotFoundException("Borrowed book not found with book id: " + borrowedBookId);
        }

    }

    @Transactional
    public void addNewBook(Long id) {
        Optional<Book> optionalBook = bookReporisoty.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            BorrowedBook borrowedBook = new BorrowedBook(book);
            borrowedBooksRepository.save(borrowedBook);
        } else {
            throw new EntityNotFoundException("Book not found with id: " + id);
        }
    }

    @Transactional
    public void deleteBorrowedBooksByBook(Long id) {
        Optional<Book> book = bookReporisoty.findById(id);
        if (book.isPresent()) {
            borrowedBooksRepository.deleteAllByBook(book.get());
        }
    }

}
