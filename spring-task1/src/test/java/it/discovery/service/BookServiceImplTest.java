package it.discovery.service;

import it.discovery.config.AppConfiguration;
import it.discovery.model.Book;
import it.discovery.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.convention.TestBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(AppConfiguration.class)
class BookServiceImplTest {

    //@MockitoBean
    @TestBean
    BookRepository bookRepository;

    @Autowired
    BookService bookService;

    static BookRepository bookRepository() {
        return Mockito.mock(BookRepository.class);
    }

    @Test
    void findBookById_idCorrect_success() {
        int bookId = 2;
        Book book = new Book();
        book.setId(bookId);
        when(bookRepository.findBookById(bookId)).thenReturn(book);

        var book2 = bookService.findBookById(book.getId());
        assertNotNull(book2);
        assertSame(book, book2);

        verify(bookRepository).findBookById(bookId);
    }
}