package org.deeplearning.exceptions;

public class SaveChartException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Unable to save Chart in (absolute Path):";

    public SaveChartException(Throwable e, String filePath) {
        super(ERROR_MESSAGE + filePath, e);
    }
}