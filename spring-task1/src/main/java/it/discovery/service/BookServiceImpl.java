package it.discovery.service;

import it.discovery.logging.Logger;
import it.discovery.model.Book;
import it.discovery.repository.BookRepository;
import lombok.Getter;
import lombok.Setter;

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

	private final List<Logger> loggers;

	public BookServiceImpl(BookRepository repository, List<Logger> loggers) {
        this.repository = repository;
		this.loggers = loggers;
        System.out.println("Using " + repository.getClass().getSimpleName() + " repository");
	}
	
	@Override
	public void saveBook(Book book) {
		repository.saveBook(book);
		
		if(cachingEnabled) {
			bookCache.put(book.getId(), book);
		}
		loggers.forEach(logger -> logger.log("Book saved: " + book));
	}
	
	@Override
	public Book findBookById(int id) {
		if(cachingEnabled && bookCache.containsKey(id)) {
			return bookCache.get(id);
		}

		
		return repository.findBookById(id);
	}

	@Override
	public List<Book> findBooks() {
		return repository.findBooks();
	}
}
