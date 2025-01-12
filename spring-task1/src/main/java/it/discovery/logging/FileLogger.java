package it.discovery.logging;

public final class FileLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("File logger: " + message);
    }
}
