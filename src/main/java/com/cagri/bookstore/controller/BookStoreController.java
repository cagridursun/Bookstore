package com.cagri.bookstore.controller;

import com.cagri.bookstore.model.Book;
import com.cagri.bookstore.service.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Created by cagri.dursun
 */

@RestController
public class BookStoreController {

    @Autowired
    private BookDao bookDao;

    @RequestMapping(value = "/book/", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> listAllBook(){
        List<Book> bookList = bookDao.listBook();

        if (bookList.isEmpty()){
            return new ResponseEntity<List<Book>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Book>>(bookList,HttpStatus.OK);
    }


    @RequestMapping(value = "/book/", method = RequestMethod.POST)
    public ResponseEntity<Void> createBook(@RequestBody Book book, UriComponentsBuilder ucBuilder) {

        if (bookDao.isBookExist(book)) {
            System.out.println("A Book with name " + book.getBookName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        } else if(StringUtils.isEmpty(book.getBookName()) || StringUtils.isEmpty(book.getAuthor())){
            System.out.println("Book name or author name must not be empty");
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        bookDao.addBook(book);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/book/{id}").buildAndExpand(book.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.POST)
    public ResponseEntity<Book> updateBook(@PathVariable("id") String id, @RequestBody Book book) {

        Book currentBook = bookDao.findBook(id);

        if (currentBook == null) {
            System.out.println("A Book with name " + book.getBookName() + " not found");
            return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
        }

        currentBook.setBookName(book.getBookName());
        currentBook.setAuthor(book.getAuthor());

        bookDao.updateBook(currentBook);

        return new ResponseEntity<Book>(currentBook, HttpStatus.OK);
    }


    @RequestMapping(value = "/book/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Book> deleteBook(@PathVariable("id") String id) {

        Book book = bookDao.findBook(id);
        if (book == null) {
            System.out.println("Unable to delete. Book with id " + id + " not found");
            return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
        }

        bookDao.deleteBook(book);
        return new ResponseEntity<Book>(HttpStatus.NO_CONTENT);
    }
}
