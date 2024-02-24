package ru.sbrf.part1.exception;

public class CallableException extends RuntimeException {
    public CallableException(String message, Exception e) {
        super(message, e);
    }
}
