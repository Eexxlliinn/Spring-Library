package com.example.springlibrary.controllers;

import com.example.springlibrary.entities.Book;
import com.example.springlibrary.forms.BookForm;
import com.example.springlibrary.services.BookService;
import com.example.springlibrary.services.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/")
public class BookController {
    private final BookService bookService;
    private final LibraryService libraryService;

    @Autowired
    public BookController(BookService bookService, LibraryService libraryService) {
        this.bookService = bookService;
        this.libraryService = libraryService;
    }

    @GetMapping
    public String cabinet() {
        return "cabinet";
    }

    @GetMapping("/books")
    public String showAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    @GetMapping("/create-book")
    public String createBook() {
        return "create_book";
    }

    @PostMapping("/create-book")
    public String createBook(BookForm form) {
        Book newBook = bookService.createBook(form.toBook());
        libraryService.addNewBook(newBook.getId());
        return "redirect:/";
    }

    @GetMapping("/update-book")
    public String updateBook(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "update_book";
    }

    @PostMapping("/update-book")
    public String updateBook(@RequestParam("id") Long id, BookForm form) {
        bookService.updateBook(id, form.toBook());
        return "redirect:/";
    }

    @GetMapping("/delete-book")
    public String deleteBook(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "delete_book";
    }

    @PostMapping("/delete-book")
    public String deleteBook(@RequestParam("id") Long id) {
        libraryService.deleteBorrowedBooksByBook(id);
        bookService.deleteBook(id);
        return "redirect:/";
    }
}

