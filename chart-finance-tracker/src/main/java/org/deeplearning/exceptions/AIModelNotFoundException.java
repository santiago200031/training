package org.deeplearning.exceptions;

public class AIModelNotFoundException extends AIModelException {

    private static final String ERROR_MESSAGE = "AI Model not Found (absolute Path): ";

    public AIModelNotFoundException(String aiModelFile) {
        super(ERROR_MESSAGE + aiModelFile);
    }

    public AIModelNotFoundException() {

    }
}