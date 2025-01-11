package it.discovery.loader;

import java.util.Arrays;
import java.util.List;

import it.discovery.repository.BookRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.discovery.model.Book;

public class SpringStarter {
	public static void main(String[] args) {
		try (var context = new AnnotationConfigApplicationContext("it.discovery")) {
			
			BookRepository repository = context.getBean(BookRepository.class);
			
			Book book = new Book();
			book.setName("Introduction into Spring 6");
			book.setPages(100);
			book.setYear(2023);
			repository.saveBook(book);

			List<Book> books = repository.findBooks();
			System.out.println(books);

			System.out.println("Total bean count = " + context.getBeanDefinitionCount());
			System.out.println("Spring bean names = " + Arrays.toString(context.getBeanDefinitionNames()));
		}

	}

}
