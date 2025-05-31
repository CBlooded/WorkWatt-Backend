package org.workwattbackend.exception;

public class HostNotFoundException extends RuntimeException {
    public HostNotFoundException(String message) {
        super(message);
    }

    public HostNotFoundException() {
        super("Host with this id doesn't exists");
    }
}
