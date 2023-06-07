package com.Bikkadit.ElectronicsStore.exceptions;

public class BadApiRequest extends RuntimeException{
    public BadApiRequest(String message) {
        super(message);
    }

    public BadApiRequest() {

    }
}
