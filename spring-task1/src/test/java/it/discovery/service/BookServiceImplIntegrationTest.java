package it.discovery.service;

import it.discovery.config.AppConfiguration;
import it.discovery.model.Book;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringJUnitConfig(AppConfiguration.class)
class BookServiceImplIntegrationTest {

    @Autowired
    BookService bookService;

    @Test
    void findBookById_idCorrect_success() {
        Book book = new Book();
        book.setName("Introduction into Spring 6");
        book.setPages(100);
        book.setYear(2023);
        bookService.saveBook(book);

        Awaitility.await().atMost(100, TimeUnit.MILLISECONDS)
                .pollInterval(Duration.ofMillis(10))
                .pollInSameThread()
                .until(() -> bookService.findBookById(book.getId()) != null);

        var book2 = bookService.findBookById(book.getId());
        assertNotNull(book2);
        assertSame(book, book2);
    }
}