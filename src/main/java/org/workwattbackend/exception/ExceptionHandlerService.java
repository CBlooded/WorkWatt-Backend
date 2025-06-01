package org.workwattbackend.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionHandlerService {
    @ExceptionHandler(
        {
            AccountNotActivatedException.class,
            ComputerNotPoweredOnException.class,
            UserAlreadyExistsException.class,
            UserNotFoundException.class,
            HostNotFoundException.class
        }
    )
    public ResponseEntity<String> handleCustomExceptions(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoFreeComputersException.class)
    public ResponseEntity<String> handleNoFreeComputersException(NoFreeComputersException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotOwnerOfComputerException.class)
    public ResponseEntity<String> handleNoFreeComputersException(UserNotOwnerOfComputerException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
