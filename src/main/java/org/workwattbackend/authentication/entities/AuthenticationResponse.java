package org.workwattbackend.authentication.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class AuthenticationResponse {
    private final String token;
}
