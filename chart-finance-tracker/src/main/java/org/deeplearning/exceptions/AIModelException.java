package org.deeplearning.exceptions;

public class AIModelException extends RuntimeException {
    public AIModelException() {
    }

    public AIModelException(String message) {
        super(message);
    }

    public AIModelException(String message, Exception e) {
        super(message, e);
    }
}