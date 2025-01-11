package it.discovery.loader;

import java.util.Arrays;
import java.util.List;

import it.discovery.repository.BookRepository;
import it.discovery.service.BookService;
import it.discovery.service.BookServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.discovery.model.Book;

public class SpringStarter {
	public static void main(String[] args) {
		try (var context = new AnnotationConfigApplicationContext("it.discovery")) {
			
			var service = context.getBean(BookServiceImpl.class);
			
			Book book = new Book();
			book.setName("Introduction into Spring 6");
			book.setPages(100);
			book.setYear(2023);
			service.saveBook(book);

			List<Book> books = service.findBooks();
			System.out.println(books);

			System.out.println("Total bean count = " + context.getBeanDefinitionCount());
			System.out.println("Spring bean names = " + Arrays.toString(context.getBeanDefinitionNames()));
			System.out.println("Repositories = " +service.getRepositories());

			var book1 = context.getBean(Book.class);
			var book2 = context.getBean(Book.class);
			System.out.println("Book equality = " + (book1 == book2));

		}

	}

}
