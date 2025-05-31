package org.workwattbackend.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.workwattbackend.authentication.entities.AuthenticationRequest;
import org.workwattbackend.authentication.entities.AuthenticationResponse;
import org.workwattbackend.authentication.entities.RegisterRequest;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(new AuthenticationResponse("1234"));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(new AuthenticationResponse("1234"));
    }
}
