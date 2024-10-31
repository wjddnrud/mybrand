package com.example.demo.controller;

import com.example.demo.domain.item.Book;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookDto {

    private Long id;

    @NotEmpty(message = "상품명은 필수입니다.")
    private String name;

    private int price;

    private int stockQuantity;

    private String author;

    private String isbn;

    public static BookDto toBookForm(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.name = book.getName();
        bookDto.price = book.getPrice();
        bookDto.stockQuantity = book.getStockQuantity();
        bookDto.author = book.getAuthor();
        bookDto.isbn = book.getIsbn();
        return bookDto;
    }

}
