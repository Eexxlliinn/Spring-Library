package com.example.springlibrary.controllers;

import com.example.springlibrary.entities.Book;
import com.example.springlibrary.entities.BorrowedBook;
import com.example.springlibrary.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/borrowed-books")
    public String showAllBorrowedBooks(Model model) {
        model.addAttribute("borrowedBooks", libraryService.getBorrowedBooks());
        return "borrowed_books";
    }

    @GetMapping("/take-book")
    public String takeBook(Model model) {
        model.addAttribute("borrowedBooks", libraryService.getAvailableBooks());
        return "take_book";
    }

    @PostMapping("/take-book")
    public String takeBook(@RequestParam("borrowedBook") Long bookId) {
        libraryService.borrowBook(bookId);
        return "redirect:/";
    }

    @GetMapping("/return-book")
    public String returnBookForm(Model model) {
        model.addAttribute("borrowedBooks", libraryService.getNotAvailableBooks());
        return "return_book";
    }

    @PostMapping("/return-book")
    public String returnBook(@RequestParam("borrowedBook") Long borrowedBookId) {
        libraryService.returnBook(borrowedBookId);
        return "redirect:/";
    }

}

