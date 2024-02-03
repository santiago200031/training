package org.deeplearning.exceptions;

public class SaveChartException extends RuntimeException {
    public SaveChartException(Throwable e, String filePath) {
        super("Unable to save file in (absolute Path):" + filePath, e);
    }
}