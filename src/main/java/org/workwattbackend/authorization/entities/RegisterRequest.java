package org.workwattbackend.authorization.entities;


import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@Getter
@RequiredArgsConstructor
public class RegisterRequest {
    private final String email;
    private final String firstName;
    private final String lastName;

    //TODO change to enum
    private final int role;
}
