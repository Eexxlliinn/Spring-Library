package com.example.springlibrary.forms;

import com.example.springlibrary.entities.Book;
import lombok.Data;

@Data
public class BookForm {
    private Long id;
    private String isbn;
    private String title;
    private String genre;
    private String description;
    private String author;

    public Book toBook() {
        return new Book(isbn, title, genre, description, author);
    }
}
