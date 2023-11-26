package org.finance.errors;

public class NoSuchDataInFile extends Exception {
    NoSuchDataInFile(Throwable e) {
        super(e);
    }

    public NoSuchDataInFile(String message) {
        super(message);
    }
}