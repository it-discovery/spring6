package it.discovery.logging;

public sealed interface Logger permits ConsoleLogger, FileLogger {
    void log(String message);
}
