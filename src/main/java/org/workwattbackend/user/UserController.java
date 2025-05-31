package org.workwattbackend.user;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
