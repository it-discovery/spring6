package it.discovery.loader;

import it.discovery.config.AppConfiguration;
import it.discovery.model.Book;
import it.discovery.proxy.MeasurementProxy;
import it.discovery.service.BookService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

public class SpringStarter {
    public static void main(String[] args) throws InterruptedException {
		try (var context = new AnnotationConfigApplicationContext(AppConfiguration.class)) {

			var service = context.getBean(BookService.class);

			var handler = new MeasurementProxy(service);
			var proxy = (BookService) Proxy.newProxyInstance(BookService.class.getClassLoader(),
					new Class[]{BookService.class}, handler);

            Thread.sleep(1000);

			Book book = new Book();
			book.setName("Introduction into Spring 6");
			book.setPages(100);
			book.setYear(2023);
			proxy.saveBook(book);

			List<Book> books = proxy.findBooks();
			System.out.println(books);

			System.out.println("Total bean count = " + context.getBeanDefinitionCount());
			System.out.println("Spring bean names = " + Arrays.toString(context.getBeanDefinitionNames()));
			//System.out.println("Repositories = " +service.getRepositories());

			var book1 = context.getBean(Book.class);
			var book2 = context.getBean(Book.class);
			System.out.println("Book equality = " + (book1 == book2));

            Thread.sleep(3000);

		}

	}

}
