package it.discovery.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import it.discovery.model.Book;
import it.discovery.repository.BookRepository;
import it.discovery.repository.DBBookRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
//@Named
public class BookServiceImpl implements BookService {
	private final BookRepository repository;
	
	private boolean cachingEnabled;
	
	private final Map<Integer, Book> bookCache = new ConcurrentHashMap<>();

	//@Inject
	private List<BookRepository> repositories;

	public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
        System.out.println("Using " + repository.getClass().getSimpleName() + " repository");
	}
	
	@Override
	public void saveBook(Book book) {
		repository.saveBook(book);
		
		if(cachingEnabled) {
			bookCache.put(book.getId(), book);
		}
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
