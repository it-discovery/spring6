package it.discovery.repository;

import it.discovery.model.Book;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles database-related book operations
 * 
 * @author morenets
 *
 */
@Getter
@Setter
//@Repository
@RequiredArgsConstructor
public class DBBookRepository implements BookRepository {
	private final Map<Integer, Book> books = new ConcurrentHashMap<>();

	private int counter = 0;

	//@Value("${db.server}")
	private final String server;

	//@Value("${db.name}")
	private final String db;

	//@PostConstruct
	void init() {
		System.out.println("Started db repository with server:" + server + " and database: " + db );
	}

	//@PreDestroy
	void destroy() {
		System.out.println("Shutting down repository ... ");
	}
	
	@Override
	public void saveBook(Book book) {
		if (book.getId() == 0) {
			counter++;
			book.setId(counter);
		}
		
		books.put(book.getId(), book);

		System.out.println("Saved book " + book);
	}
	
	@Override
	public Book findBookById(int id) {
		return books.get(id);
	}

	@Override
	public List<Book> findBooks() {
		return new ArrayList<>(books.values());
	}
	

}
