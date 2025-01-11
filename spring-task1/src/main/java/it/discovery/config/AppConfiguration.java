package it.discovery.config;

import it.discovery.repository.BookRepository;
import it.discovery.repository.DBBookRepository;
import it.discovery.service.BookService;
import it.discovery.service.BookServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration(proxyBeanMethods = false)
@ComponentScan("it.discovery.config.factory")
public class AppConfiguration {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    BookRepository bookRepository() {
        return new DBBookRepository();
    }

    @Bean(bootstrap = Bean.Bootstrap.BACKGROUND)
    BookService bookService(BookRepository bookRepository) {
        return new BookServiceImpl(bookRepository);
    }

    @Bean
    Executor bootstrapExecutor() {
        return Executors.newCachedThreadPool();
    }
}
