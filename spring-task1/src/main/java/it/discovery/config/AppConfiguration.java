package it.discovery.config;

import it.discovery.model.Book;
import it.discovery.repository.BookRepository;
import it.discovery.repository.DBBookRepository;
import it.discovery.repository.XMLBookRepository;
import it.discovery.service.BookService;
import it.discovery.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration(proxyBeanMethods = false)
@ComponentScan("it.discovery.config.factory")
public class AppConfiguration {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    @Qualifier("db")
    BookRepository dbBookRepository() {
        return new DBBookRepository();
    }

    @Qualifier("xml")
    @Bean(initMethod = "init", destroyMethod = "destroy")
    @Primary
    BookRepository xmlBookRepository() {
        return new XMLBookRepository();
    }

    @Bean(bootstrap = Bean.Bootstrap.BACKGROUND)
    BookService bookService(BookRepository bookRepository) {
        return new BookServiceImpl(bookRepository);
    }

    @Bean
    Executor bootstrapExecutor() {
        return Executors.newCachedThreadPool();
    }

    @Lazy
    @Fallback
    @Bean(autowireCandidate = false)
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    Book book() {
        return new Book();
    }
}
