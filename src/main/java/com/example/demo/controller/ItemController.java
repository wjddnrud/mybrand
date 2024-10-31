package com.example.demo.controller;

import com.example.demo.domain.item.Book;
import com.example.demo.domain.item.ItemEntity;
import com.example.demo.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("bookForm", new BookDto());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(@Valid BookDto form, BindingResult result) {

        if (result.hasErrors()) {
            return "items/createItemForm";
        }

        Book book = new Book();
        book.setName(form.getName());
        book.setAuthor(form.getAuthor());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<ItemEntity> itemEntities = itemService.findItems();
        // List<BookForm> bookForms = (items.stream().map(BookForm::toBookForm).toList());
        model.addAttribute("items", itemEntities);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);

        BookDto bookDto = new BookDto();
        bookDto.setId(item.getId());
        bookDto.setName(item.getName());
        bookDto.setPrice(item.getPrice());
        bookDto.setStockQuantity(item.getStockQuantity());
        bookDto.setAuthor(item.getAuthor());
        bookDto.setIsbn(item.getIsbn());

        model.addAttribute("bookForm", bookDto);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") @Valid BookDto form, BindingResult result) {
        if (result.hasErrors()) {
            return "items/updateItemForm";
        }

        Book book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);

//        Item item = itemService.findOne(form.getId());
//        item.setName(form.getName());
//        item.setPrice(form.getPrice());
//        item.setStockQuantity(form.getStockQuantity());

        return "redirect:/items";
    }


}

