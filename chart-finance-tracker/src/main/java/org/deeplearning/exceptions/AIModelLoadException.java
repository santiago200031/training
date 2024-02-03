package org.deeplearning.exceptions;

public class AIModelLoadException extends AIModelException {

    private static final String ERROR_MESSAGE = "Failed to load model (absolute Path): ";

    public AIModelLoadException() {
    }

    public AIModelLoadException(String aiModelFile, Exception e) {
        super(ERROR_MESSAGE + aiModelFile, e);
    }

    public AIModelLoadException(String aiModelFile) {
        super(ERROR_MESSAGE + aiModelFile);
    }
}
