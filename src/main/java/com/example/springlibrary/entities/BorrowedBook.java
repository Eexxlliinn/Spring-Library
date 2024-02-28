package com.example.springlibrary.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "borrowed_books")
public class BorrowedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "book", referencedColumnName = "id")
    private Book book;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "borrowed_date")
    private LocalDate borrowedDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "borrowed_time")
    private LocalTime borrowedTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "return_date")
    private LocalDate returnDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "return_time")
    private LocalTime returnTime;

    public BorrowedBook(Book book) {
        this.book = book;
    }
}
