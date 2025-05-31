package org.workwattbackend.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.workwattbackend.authentication.entities.AuthenticationRequest;
import org.workwattbackend.authentication.entities.AuthenticationResponse;
import org.workwattbackend.authentication.entities.RegisterRequest;

/**
 * The type Authentication controller.
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    /**
     * Authenticate response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    /**
     * Register response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        service.register(request);
        return ResponseEntity.ok().build();
    }
}
