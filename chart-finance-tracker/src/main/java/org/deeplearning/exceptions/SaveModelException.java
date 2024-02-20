package org.deeplearning.exceptions;

public class SaveModelException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Unable to save Model in (absolute Path):";

    public SaveModelException(Throwable e, String filePath) {
        super(ERROR_MESSAGE + filePath, e);
    }
}