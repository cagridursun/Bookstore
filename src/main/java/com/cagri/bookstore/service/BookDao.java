package com.cagri.bookstore.service;

import com.cagri.bookstore.model.Book;

import java.util.List;

/**
 * Created by cagri.dursun
 */
public interface BookDao {

    Book addBook(Book book);
    List<Book> listBook();
    Book findBook(String id);
    Book findBookByName(String bookName);
    boolean isBookExist(Book book);
    void deleteBook(Book book);
    Book updateBook(Book book);
}
