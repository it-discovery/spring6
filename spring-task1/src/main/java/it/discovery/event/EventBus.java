package it.discovery.event;

import it.discovery.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

import java.util.List;

@RequiredArgsConstructor
public class EventBus {

    private final List<Logger> loggers;

    @EventListener
    void handle(LogEvent event) {
        loggers.forEach(logger -> logger.log(event.message()));
    }
}
