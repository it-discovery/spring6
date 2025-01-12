package it.discovery.config;

import it.discovery.logging.ConsoleLogger;
import it.discovery.logging.FileLogger;
import it.discovery.logging.Logger;
import it.discovery.model.Book;
import it.discovery.profile.CurrentProfile;
import it.discovery.profile.Profile;
import it.discovery.repository.BookRepository;
import it.discovery.repository.DBBookRepository;
import it.discovery.repository.XMLBookRepository;
import it.discovery.service.BookService;
import it.discovery.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration(proxyBeanMethods = false)
@ComponentScan("it.discovery.config.factory")
@PropertySource("application.properties")
public class AppConfiguration {

    public static class PersistenceConfiguration {
        @Bean(initMethod = "init", destroyMethod = "destroy")
        @Qualifier("db")
        @CurrentProfile(Profile.PROD)
        BookRepository dbBookRepository(Environment env) {
            return new DBBookRepository(env.getRequiredProperty("db.server"),
                    env.getRequiredProperty("db.name"));
        }

        @CurrentProfile(Profile.DEV)
        @Qualifier("xml")
        @Bean(initMethod = "init", destroyMethod = "destroy")
            //@Primary
        BookRepository xmlBookRepository() {
            return new XMLBookRepository();
        }
    }

    @Bean(bootstrap = Bean.Bootstrap.BACKGROUND)
    BookService bookService(BookRepository bookRepository, List<Logger> loggers) {
        return new BookServiceImpl(bookRepository, loggers);
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

    public static class LogConfiguration {
        @Bean
        Logger fileLogger() {
            return new FileLogger();
        }

        @Bean
        Logger consoleLogger() {
            return new ConsoleLogger();
        }
    }
}
