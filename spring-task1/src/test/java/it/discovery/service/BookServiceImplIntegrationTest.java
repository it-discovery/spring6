package it.discovery.service;

import it.discovery.config.AppConfiguration;
import it.discovery.event.LogEvent;
import it.discovery.model.Book;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(AppConfiguration.class)
@RecordApplicationEvents
class BookServiceImplIntegrationTest {

    @Autowired
    BookService bookService;

    @Autowired
    ApplicationEvents events;

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

        var logEventCount = events.stream().filter(event ->
                event instanceof PayloadApplicationEvent<?> payloadEvent &&
                        payloadEvent.getPayload() instanceof LogEvent).count();
        assertEquals(1, logEventCount);
    }
}