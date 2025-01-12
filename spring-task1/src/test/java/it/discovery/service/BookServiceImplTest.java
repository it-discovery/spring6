package it.discovery.service;

import it.discovery.config.AppConfiguration;
import it.discovery.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringJUnitConfig(AppConfiguration.class)
class BookServiceImplTest {

    @Autowired
    BookService bookService;

    @Test
    void findBookById_idCorrect_success() throws InterruptedException {
        Book book = new Book();
        book.setName("Introduction into Spring 6");
        book.setPages(100);
        book.setYear(2023);
        bookService.saveBook(book);

        Thread.sleep(100);

        var book2 = bookService.findBookById(book.getId());
        assertNotNull(book2);
        assertSame(book, book2);
    }
}