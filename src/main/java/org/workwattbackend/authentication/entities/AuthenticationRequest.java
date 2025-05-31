package org.workwattbackend.authentication.entities;


import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@Getter
public class AuthenticationRequest {
    private final String email;
    private final String password;
}
