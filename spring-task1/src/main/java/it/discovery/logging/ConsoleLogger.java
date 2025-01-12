package it.discovery.logging;

public final class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("Console logger: " + message);

    }
}
