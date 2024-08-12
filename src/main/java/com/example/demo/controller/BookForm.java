package com.example.demo.controller;

import com.example.demo.domain.item.Book;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {

    private Long id;

    @NotEmpty(message = "상품명은 필수입니다.")
    private String name;

    private int price;

    private int stockQuantity;

    private String author;

    private String isbn;

    public static BookForm toBookForm(Book book) {
        BookForm bookForm = new BookForm();
        bookForm.name = book.getName();
        bookForm.price = book.getPrice();
        bookForm.stockQuantity = book.getStockQuantity();
        bookForm.author = book.getAuthor();
        bookForm.isbn = book.getIsbn();
        return bookForm;
    }

}
