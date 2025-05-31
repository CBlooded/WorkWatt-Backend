package org.workwattbackend.exception;

public class AccountNotActivatedException extends RuntimeException {
    public AccountNotActivatedException(String message) {
        super(message);
    }

    public AccountNotActivatedException() {
        super("This account haven't been activated yet.");
    }
}
