package com.cagri.bookstore.service.impl;

import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;

import com.cagri.bookstore.model.Book;
import com.cagri.bookstore.service.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;

/**
 * Created by cagri.dursun on 7.6.2016.
 */

@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public static final String COLLECTION_NAME = "book";

    public Book addBook(Book book) {
        Assert.notNull(book.getBookName(),"Book name must not be empty");
        Assert.notNull(book.getAuthor(), "Author name must not be empty");
        if (!this.mongoTemplate.collectionExists(Book.class)) {
            mongoTemplate.createCollection(Book.class);
        }
        book.setId(UUID.randomUUID().toString());
        mongoTemplate.insert(book, COLLECTION_NAME);

        return book;
    }

    public List<Book> listBook() {
        return mongoTemplate.findAll(Book.class, COLLECTION_NAME);
    }

    public Book findBook(String id){
        Query query = query(where("_id").is(id));
        return mongoTemplate.findOne(query,Book.class);
    }

    public Book findBookByName(String bookName) {
        Query query = query(where("bookName").is(bookName));
        return mongoTemplate.findOne(query,Book.class);
    }

    public boolean isBookExist(Book book) {
        return findBookByName(book.getBookName()) != null;
    }

    public void deleteBook(Book book) {
        mongoTemplate.remove(book, COLLECTION_NAME);
    }

    public Book updateBook(Book book) {
        mongoTemplate.save(book, COLLECTION_NAME);
        return book;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }
}
