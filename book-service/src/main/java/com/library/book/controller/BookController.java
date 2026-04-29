package com.library.book.controller;

import com.library.book.model.Book;
import com.library.book.service.BookCatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookCatalogService bookCatalogService;

    public BookController(BookCatalogService bookCatalogService) {
        this.bookCatalogService = bookCatalogService;
    }

    @GetMapping
    public List<Book> getAll() {
        return bookCatalogService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        return bookCatalogService.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Book create(@RequestBody Book book) {
        return bookCatalogService.save(book);
    }
}
