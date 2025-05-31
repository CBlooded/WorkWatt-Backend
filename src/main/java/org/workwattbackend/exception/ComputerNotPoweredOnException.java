package org.workwattbackend.exception;

public class ComputerNotPoweredOnException extends RuntimeException {
    public ComputerNotPoweredOnException(Long computerId) {
        super("Computer " + computerId + " is not powered on");
    }
}
