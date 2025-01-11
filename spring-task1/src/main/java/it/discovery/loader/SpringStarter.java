package it.discovery.loader;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.discovery.model.Book;
import it.discovery.repository.DBBookRepository;

public class SpringStarter {
	public static void main(String[] args) {
		try (var context = new AnnotationConfigApplicationContext("it.discovery")) {
			
			DBBookRepository repository = null; //TODO Load from context
			
			Book book = new Book();
			book.setName("Introduction into Spring 6");
			book.setPages(100);
			book.setYear(2023);
			repository.saveBook(book);

			List<Book> books = repository.findBooks();
			System.out.println(books);
		}

	}

}
