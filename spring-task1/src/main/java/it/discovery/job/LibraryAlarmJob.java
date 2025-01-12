package it.discovery.job;

import it.discovery.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class LibraryAlarmJob {

    private final BookService bookService;

    @Scheduled(initialDelay = 500, fixedRate = 1000)
    void alarm() {
        System.out.println("Alarm job started");
        if (bookService.findBooks().isEmpty()) {
            System.out.println("ALARM! Library is empty");
        }
    }
}
