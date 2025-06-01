package org.workwattbackend.exception;

public class UserNotOwnerOfComputerException extends RuntimeException {
    public UserNotOwnerOfComputerException(String message) {
        super(message);
    }
    public UserNotOwnerOfComputerException() {
        super("User isn't owner of computer. Access denied.");
    }
}
