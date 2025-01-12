package it.discovery.service;

import it.discovery.event.LogEvent;
import it.discovery.model.Book;
import it.discovery.repository.BookRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
//@Named
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    private boolean cachingEnabled;

    private final Map<Integer, Book> bookCache = new ConcurrentHashMap<>();

    //@Inject
    private List<BookRepository> repositories;

    private final ApplicationEventPublisher publisher;

    public BookServiceImpl(BookRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
        System.out.println("Using " + repository.getClass().getSimpleName() + " repository");
    }

    @Override
    public void saveBook(Book book) {
        repository.saveBook(book);

        if (cachingEnabled) {
            bookCache.put(book.getId(), book);
        }
        publisher.publishEvent(new LogEvent("Book saved: " + book));
    }

    @Override
    public Book findBookById(int id) {
        if (cachingEnabled && bookCache.containsKey(id)) {
            return bookCache.get(id);
        }


        return repository.findBookById(id);
    }

    @Override
    public List<Book> findBooks() {
        return repository.findBooks();
    }
}
