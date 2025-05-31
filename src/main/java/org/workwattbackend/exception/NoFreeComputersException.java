package org.workwattbackend.exception;

public class NoFreeComputersException extends RuntimeException {
    public NoFreeComputersException() {
        super("No free computers");
    }
}
