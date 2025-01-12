package it.discovery.service;

import it.discovery.bpp.Init;
import it.discovery.cache.CacheStorage;
import it.discovery.event.LogEvent;
import it.discovery.model.Book;
import it.discovery.repository.BookRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
//@Named
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    //private boolean cachingEnabled;

    private final Map<Integer, Book> bookCache = new ConcurrentHashMap<>();

    //@Inject
    private List<BookRepository> repositories;

    private final ApplicationEventPublisher publisher;

    private final CacheStorage<Integer, Book> cacheStorage;

    public BookServiceImpl(BookRepository repository, ApplicationEventPublisher publisher, CacheStorage<Integer, Book> cacheStorage) {
        this.repository = repository;
        this.publisher = publisher;
        this.cacheStorage = cacheStorage;
        System.out.println("Using " + repository.getClass().getSimpleName() + " repository");
    }

    @Init
    void setup(ApplicationContext context) {
        System.out.println("Custom service initialization with context: " + context);
        System.out.println("Caching is " + context.getEnvironment().getProperty("caching.enabled"));
    }

    @Override
    @Async
    public void saveBook(Book book) {
        repository.saveBook(book);

//        if (cachingEnabled) {
//            bookCache.put(book.getId(), book);
//        }
        cacheStorage.put(book.getId(), book);
        publisher.publishEvent(new LogEvent("Book saved: " + book));
    }

    @Override
    public Book findBookById(int id) {
//        if (cachingEnabled && bookCache.containsKey(id)) {
//            return bookCache.get(id);
//        }

        return cacheStorage.get(id).orElseGet(() -> repository.findBookById(id));
    }

    @Override
    public List<Book> findBooks() {
        return repository.findBooks();
    }
}
