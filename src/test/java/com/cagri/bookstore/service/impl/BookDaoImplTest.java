package com.cagri.bookstore.service.impl;

import com.cagri.bookstore.model.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by cagri.dursun on 8.6.2016.
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(value = {"classpath*:testContext.xml"})
public class BookDaoImplTest{

    @Mock
    private BookDaoImpl bookDao;

    private static Book book1,book2;

    @Autowired
    MongoTemplate mongoTemplate;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);


        bookDao.setMongoTemplate(mongoTemplate);

        book1 = new Book();
        book1.setId("B1");
        book1.setBookName("The Lord of the Ring");
        book1.setAuthor("J.R.R. Tolkien");

        book2 = new Book();
        book2.setId("B1");
        book2.setBookName("Hobbit");
        book2.setAuthor("J.R.R. Tolkien");

        when(bookDao.addBook(book1)).thenReturn(book1);
        when(bookDao.findBookByName(book1.getBookName())).thenReturn(book1);
        when(bookDao.findBook(book1.getId())).thenReturn(book1);
        when(bookDao.listBook()).thenReturn(Arrays.asList(book1));
        when(bookDao.updateBook(book1)).thenReturn(book2);
        when(bookDao.isBookExist(book1)).thenReturn(true);
    }

    @Test
    public void testMockCreation(){
        Assert.notNull(bookDao);
    }

    @Test

    public void testAddBook(){
        Book book = bookDao.addBook(book1);

        assertNotNull(book);
        assertEquals(book,book1);
    }

    @Test
    public void testFindBook(){
        String id = "B1";
        Book book = bookDao.findBook(id);

        assertNotNull(book);
        assertEquals(id,book.getId());
        assertEquals("The Lord of the Ring",book.getBookName());
        assertEquals("J.R.R. Tolkien",book.getAuthor());
    }

    @Test
    public void testFindBookByName(){
        String bookName = "The Lord of the Ring";
        Book book = bookDao.findBookByName(bookName);

        assertNotNull(book);
        assertEquals("B1",book.getId());
        assertEquals(bookName,book.getBookName());
        assertEquals("J.R.R. Tolkien",book.getAuthor());
    }

    @Test
    public void testListBook(){
        List<Book> bookList = bookDao.listBook();

        assertEquals(1,bookList.size());

        Book book = bookList.get(0);
        assertNotNull(book);
        assertEquals("B1",book.getId());
        assertEquals("The Lord of the Ring",book.getBookName());
        assertEquals("J.R.R. Tolkien",book.getAuthor());
    }

    @Test
    public void testUpdateBook(){
        Book oldBook = bookDao.findBook("B1");

        oldBook.setBookName("Hobbit");
        Book newBook = bookDao.updateBook(oldBook);

        assertNotNull(newBook);
        assertEquals(oldBook.getId(),newBook.getId());
        assertEquals("Hobbit",newBook.getBookName());
        assertEquals(oldBook.getAuthor(),newBook.getAuthor());
    }

    @Test
    public void testIsBookExist(){
        boolean exist = bookDao.isBookExist(book1);

        assertTrue(exist);
    }

}
