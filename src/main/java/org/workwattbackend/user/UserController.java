package org.workwattbackend.user;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.workwattbackend.exception.HostNotFoundException;
import org.workwattbackend.exception.UserNotFoundException;

/**
 * The type User controller.
 */
@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    /**
     * Sets user password and activate account.
     *
     * @param hostId      the host id
     * @param newPassword the new password
     * @return the user password and activate account
     */
    @PostMapping("/password/set")
    public ResponseEntity<Void> setUserPasswordAndActivateAccount(@RequestParam(name = "h") String hostId, @RequestParam(name = "n") String newPassword) {
        var user = service.getUserFromHostId(hostId);
        service.changePasswordAndActivate(user.getId(), newPassword);
        return ResponseEntity.ok().build();
    }

    /**
     * Validate activation host response entity.
     *
     * @param hostId       the host id
     * @param tempPassword the temp password
     * @return the response entity
     * @throws HostNotFoundException the host not found exception
     * @throws UserNotFoundException the user not found exception
     */
    @GetMapping("/password/host/validate")
    public ResponseEntity<Void> validateActivationHost(@RequestParam(name = "h") String hostId, @RequestParam(name = "t") String tempPassword) throws HostNotFoundException, UserNotFoundException {
        if (!service.isHostIdValid(hostId))
            throw new HostNotFoundException();
        var user = service.getUserFromHostId(hostId);
        if (!service.isTempPasswordValid(user.getId(), tempPassword))
            throw new UserNotFoundException();
        return ResponseEntity.ok().build();
    }
}
