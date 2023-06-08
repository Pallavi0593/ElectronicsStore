package com.Bikkadit.ElectronicsStore.exceptions;

public class BadApiRequestException extends RuntimeException{
    public BadApiRequestException(String message) {
        super(message);
    }

    public BadApiRequestException() {
super("BAD Request!!");
    }
}
